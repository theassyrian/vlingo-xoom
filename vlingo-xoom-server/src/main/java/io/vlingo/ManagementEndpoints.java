package io.vlingo;

import io.micronaut.context.ApplicationContext;
import io.micronaut.management.endpoint.beans.BeansEndpoint;
import io.micronaut.management.endpoint.env.EnvironmentEndpoint;
import io.micronaut.management.endpoint.health.HealthEndpoint;
import io.micronaut.management.endpoint.loggers.LoggersEndpoint;
import io.micronaut.management.endpoint.routes.RoutesEndpoint;
import io.vlingo.annotations.Resource;
import io.vlingo.common.Completes;
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

    public Completes<BeansEndpoint> getBeansEndpoint() {
        return Completes.withSuccess(applicationContext.getBean(BeansEndpoint.class));
    }

    public Completes<HealthEndpoint> getHealthEndpoint() {
        return Completes.withSuccess(applicationContext.getBean(HealthEndpoint.class));
    }

    public Completes<RoutesEndpoint> getRoutesEndpoint() {
        return Completes.withSuccess(applicationContext.getBean(RoutesEndpoint.class));
    }

    public Completes<EnvironmentEndpoint> getEnvironmentEndpoint() {
        return Completes.withSuccess(applicationContext.getBean(EnvironmentEndpoint.class));
    }

    public Completes<LoggersEndpoint> getLoggersEndpoint() {
        return Completes.withSuccess(applicationContext.getBean(LoggersEndpoint.class));
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
                get("/health").handle(() -> response(Ok, getHealthEndpoint()
                        .andThen(healthEndpoint -> healthEndpoint.getHealth(null).blockingGet())))
                        .onError(this::getErrorResponse),
                get("/beans").handle(() -> response(Ok, getBeansEndpoint()
                        .andThen(beansEndpoint -> beansEndpoint.getBeans().blockingGet())))
                        .onError(this::getErrorResponse),
                get("/routes").handle(() -> response(Ok, getRoutesEndpoint()
                        .andThen(routesEndpoint -> routesEndpoint.getRoutes().blockingGet())))
                        .onError(this::getErrorResponse),
                get("/env").handle(() -> response(Ok, getEnvironmentEndpoint()
                        .andThen(EnvironmentEndpoint::getEnvironmentInfo)))
                        .onError(this::getErrorResponse),
                get("/loggers").handle(() -> response(Ok, getLoggersEndpoint()
                        .andThen(loggersEndpoint -> loggersEndpoint.loggers().blockingGet())))
                        .onError(this::getErrorResponse)

        };
    }
}
