package io.examples.domain;

import io.examples.domain.processor.ProcessorService;
import io.examples.repository.OrganizationRepository;
import io.vlingo.actors.Logger;
import io.vlingo.common.Completes;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import java.util.function.Consumer;

@Singleton
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final ProcessorService processorService;

    public OrganizationService(OrganizationRepository organizationRepository, ProcessorService processorService) {
        this.organizationRepository = organizationRepository;
        this.processorService = processorService;
    }

    public Completes<Organization> queryOrganization(Long id) {
        return organizationRepository.findById(id).map(Completes::withSuccess).orElseThrow(() ->
                new RuntimeException("Organization with id[" + id + "] does not exist"));
    }

    private Completes<Organization> executeCommand(@NotNull Long id, Consumer<Organization> commandHandler) {
        return queryOrganization(id)
                .andThenConsume(commandHandler)
                .andThen(organization -> {
                    // The command succeeded, now persist the updated state to the repository
                    Logger.basicLogger().info("Updated organization: " + organization.toString());
                    organizationRepository.update(id, organization.getStatus(), organization.getVersion());
                    return organization;
                });
    }
}
