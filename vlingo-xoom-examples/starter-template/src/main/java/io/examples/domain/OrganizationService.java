package io.examples.domain;

import io.examples.domain.processor.ProcessorService;
import io.examples.repository.OrganizationRepository;
import io.reactivex.Observable;
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

    public Completes<Organization> createOrganization(Organization organization) {
        organization = organizationRepository.save(organization);
        execute(organization.getId(), result -> result.create(processorService.getProcessor()));
        return getOrganization(organization.getId());
    }

    public Completes<Organization> confirmOrganization(Long id) {
        execute(id, result -> result.confirm(processorService.getProcessor()));
        return getOrganization(id);
    }

    private Completes<Organization> execute(@NotNull Long id, Consumer<Organization> commandHandler) {
        return getOrganization(id).andThenConsume(commandHandler)
                .andThenConsume(organization -> organizationRepository
                        .update(id, organization.getStatus(), organization.getVersion()));
    }
}
