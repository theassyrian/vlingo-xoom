package io.examples.repository;

import io.examples.domain.Organization;
import io.examples.domain.OrganizationStatus;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Long> {

    void update(@Id Long id, OrganizationStatus status, String version);

}
