package io.examples.endpoint;

import io.examples.domain.Organization;
import io.examples.domain.OrganizationService;
import io.vlingo.xoom.annotations.Resource;
import io.vlingo.common.Completes;
import io.vlingo.http.Response;
import io.vlingo.http.resource.RequestHandler;
import io.vlingo.xoom.resource.Endpoint;

import static io.vlingo.common.Completes.withSuccess;
import static io.vlingo.http.Response.Status.*;
import static io.vlingo.http.resource.ResourceBuilder.*;

@Resource
public class OrganizationEndpoint implements Endpoint {

    private static final String ENDPOINT_VERSION = "1.1";
    private static final String ENDPOINT_NAME = "Organization";
    private final OrganizationService organizationService;

    public OrganizationEndpoint(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @Override
    public RequestHandler[] getHandlers() {
        return new RequestHandler[]{
                get("/v1/organizations")
                        .handle(this::findAllOrganizations)
                        .onError(this::getErrorResponse),
                get("/v1/organizations/{id}")
                        .param(Long.class)
                        .handle(this::findOrganizationById)
                        .onError(this::getErrorResponse),
                post("/v1/organizations")
                        .body(Organization.class)
                        .handle(this::createOrganization)
                        .onError(this::getErrorResponse),
                delete("/v1/organizations/{id}")
                        .param(Long.class)
                        .handle(this::deleteOrganization)
                        .onError(this::getErrorResponse),
                get("/v1/organizations/{id}/confirm")
                        .param(Long.class)
                        .handle(this::confirmOrganization)
                        .onError(this::getErrorResponse)
        };
    }

    public Completes<Response> findAllOrganizations() {
        return response(Ok, organizationService.getOrganizations());
    }

    public Completes<Response> findOrganizationById(Long id) {
        return response(Ok, organizationService.getOrganization(id));
    }

    public Completes<Response> createOrganization(Organization organization) {
        return response(Created, organizationService.createOrganization(organization));
    }

    public Completes<Response> deleteOrganization(Long id) {
        return emptyResponse(NoContent, withSuccess(() -> organizationService.deleteOrganization(id)));
    }

    public Completes<Response> confirmOrganization(Long id) {
        return response(Created, organizationService.confirmOrganization(id));
    }

    @Override
    public String getName() {
        return ENDPOINT_NAME;
    }

    @Override
    public String getVersion() {
        return OrganizationEndpoint.ENDPOINT_VERSION;
    }
}
