package io.vlingo;

import io.micronaut.context.ApplicationContext;
import io.micronaut.management.endpoint.beans.BeansEndpoint;
import io.micronaut.management.endpoint.env.EnvironmentEndpoint;
import io.micronaut.management.endpoint.health.HealthEndpoint;
import io.micronaut.management.endpoint.loggers.LoggersEndpoint;
import io.micronaut.management.endpoint.routes.RoutesEndpoint;
import io.vlingo.annotations.Resource;
import io.vlingo.http.resource.RequestHandler;
import io.vlingo.resource.Endpoint;

import static io.vlingo.http.Response.Status.Ok;
import static io.vlingo.http.resource.ResourceBuilder.get;

@Resource
public class ManagementEndpoints implements Endpoint {

    private final String ENDPOINT_NAME = "Management Endpoint";
    private final String ENDPOINT_VERSION = "1.0.0";
    private final ApplicationContext applicationContext;

    public ManagementEndpoints(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public BeansEndpoint getBeansEndpoint() {
        return applicationContext.getBean(BeansEndpoint.class);
    }

    public HealthEndpoint getHealthEndpoint() {
        return applicationContext.getBean(HealthEndpoint.class);
    }

    public RoutesEndpoint getRoutesEndpoint() {
        return applicationContext.getBean(RoutesEndpoint.class);
    }

    public EnvironmentEndpoint getEnvironmentEndpoint() {
        return applicationContext.getBean(EnvironmentEndpoint.class);
    }

    public LoggersEndpoint getLoggersEndpoint() {
        return applicationContext.getBean(LoggersEndpoint.class);
    }

    @Override
    public String getName() {
        return ENDPOINT_NAME;
    }

    @Override
    public String getVersion() {
        return ENDPOINT_VERSION;
    }

    @Override
    public RequestHandler[] getHandlers() {
        return new RequestHandler[]{
                get("/health")
                        .handle(() -> getResponse(Ok, () -> getHealthEndpoint().getHealth(null).blockingGet()))
                        .onError(this::getErrorResponse),
                get("/beans")
                        .handle(() -> getResponse(Ok, () -> getBeansEndpoint().getBeans().blockingGet()))
                        .onError(this::getErrorResponse),
                get("/routes")
                        .handle(() -> getResponse(Ok, () -> getRoutesEndpoint().getRoutes().blockingGet()))
                        .onError(this::getErrorResponse),
                get("/env")
                        .handle(() -> getResponse(Ok, () -> getEnvironmentEndpoint().getEnvironmentInfo()))
                        .onError(this::getErrorResponse),
                get("/loggers")
                        .handle(() -> getResponse(Ok, () -> getLoggersEndpoint().loggers().blockingGet()))
                        .onError(this::getErrorResponse)

        };
    }
}
