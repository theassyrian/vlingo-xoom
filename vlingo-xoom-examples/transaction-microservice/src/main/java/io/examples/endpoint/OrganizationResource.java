package io.examples.endpoint;

import io.examples.domain.OrganizationService;
import io.vlingo.common.Completes;
import io.vlingo.http.Response;
import io.vlingo.http.resource.RequestHandler;
import io.vlingo.xoom.annotations.Resource;
import io.vlingo.xoom.resource.Endpoint;

import static io.vlingo.http.Response.Status.Ok;
import static io.vlingo.http.resource.ResourceBuilder.get;

@Resource
public class OrganizationResource implements Endpoint {

    private static final String ENDPOINT_VERSION = "1.0.0";
    private static final String ENDPOINT_NAME = "Organization";
    private final OrganizationService organizationService;

    public OrganizationResource(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @Override
    public RequestHandler[] getHandlers() {
        return new RequestHandler[]{
                get("/organizations/{id}")
                        .param(Long.class)
                        .handle(this::queryOrganization)
                        .onError(this::getErrorResponse)
        };
    }

    public Completes<Response> queryOrganization(Long id) {
        return response(Ok, organizationService.queryOrganization(id));
    }

    @Override
    public String getName() {
        return ENDPOINT_NAME;
    }

    @Override
    public String getVersion() {
        return OrganizationResource.ENDPOINT_VERSION;
    }
}
