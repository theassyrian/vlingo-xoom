package io.vlingo.xoom;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.discovery.DiscoveryClient;
import io.micronaut.discovery.event.ServiceStartedEvent;
import io.micronaut.health.HealthStatus;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import io.reactivex.Single;
import io.vlingo.common.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

@Singleton
@Requires(property = "eureka.client.registration.enabled", value = "true")
public class StartupHandler implements ApplicationEventListener<ServerStartupEvent> {
    private static final Logger log = LoggerFactory.getLogger(StartupHandler.class);

    @Override
    public void onApplicationEvent(ServerStartupEvent event) {
        Scheduler scheduler = new Scheduler();
        scheduler.schedule((scheduled, data) -> {
                    try {
                        //noinspection ResultOfMethodCallIgnored
                        Single.fromPublisher(data.getServiceIds())
                                .timeout(2000, TimeUnit.MILLISECONDS)
                                .doOnError((throwable) -> {
                                    log.info("Could not reach the discovery service, attempting retry...");
                                    ((VlingoServerInstance) ((VlingoServer) event.getSource()).getServiceInstance())
                                            .setHealthStatus(HealthStatus.DOWN);
                                }).doOnSuccess(strings -> {
                            log.info("Successfully contacted the discovery service for registration.");
                            if (((VlingoServer) event.getSource()).getServiceInstance()
                                    .getHealthStatus().getName().equals(HealthStatus.NAME_DOWN)) {
                                ((VlingoServerInstance) ((VlingoServer) event.getSource()).getServiceInstance())
                                        .setHealthStatus(HealthStatus.UP);
                                event.getSource().getApplicationContext()
                                        .publishEvent(new ServiceStartedEvent(((VlingoServer) event.getSource())
                                                .getServiceInstance()));
                            }
                        }).blockingGet();

                        // Close the scheduler if there is no error after the blocking get
                        scheduler.close();
                    } catch (Exception ex) {
                        log.trace(ex.getMessage());
                    }
                }, event.getSource().getApplicationContext().getBean(DiscoveryClient.class),
                5000, 5000);
    }
}
