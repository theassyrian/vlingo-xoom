package io.examples.domain;

import io.examples.domain.processor.ProcessorService;
import io.examples.repository.OrganizationRepository;
import io.reactivex.Observable;
import io.vlingo.actors.Logger;
import io.vlingo.common.Completes;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.function.Consumer;

@Singleton
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final ProcessorService processorService;

    public OrganizationService(OrganizationRepository organizationRepository, ProcessorService processorService) {
        this.organizationRepository = organizationRepository;
        this.processorService = processorService;
    }

    public Completes<List<Organization>> getOrganizations() {
        return Completes.withSuccess(organizationRepository.findAll())
                .andThen(organizations -> Observable.fromIterable(organizations).toList().blockingGet());
    }

    public Completes<Organization> getOrganization(Long id) {
        return organizationRepository.findById(id).map(Completes::withSuccess).orElseThrow(() ->
                new RuntimeException("Organization with id[" + id + "] does not exist"));
    }

    public void deleteOrganization(Long id) {
        organizationRepository.deleteById(id);
    }

    public Completes<Organization> createOrganization(Organization model) {
        // Execute the create command on a new organization entity
        return executeCommand(organizationRepository.save(model).getId(),
                organization ->
                        organization.create(processorService.getProcessor()))
                .andThenConsume(organization ->
                        getOrganization(organization.getId()))
                .otherwise(organization -> {
                    throw new RuntimeException("Could not create the organization: " + model.toString());
                });
    }

    public Completes<Organization> confirmOrganization(Long organizationId) {
        // Execute the confirm command on the organization
        return executeCommand(organizationId,
                organization -> organization.confirm(processorService.getProcessor()))
                .otherwise(organization -> {
                    throw new RuntimeException("Could not confirm the organization: " + organization.toString());
                });
    }

    private Completes<Organization> executeCommand(@NotNull Long id, Consumer<Organization> commandHandler) {
        return getOrganization(id)
                .andThenConsume(commandHandler)
                .andThen(organization -> {
                    // The command succeeded, now persist the updated state to the repository
                    Logger.basicLogger().info("Updated organization: " + organization.toString());
                    organizationRepository.update(id, organization.getStatus(), organization.getVersion());
                    return organization;
                });
    }
}
