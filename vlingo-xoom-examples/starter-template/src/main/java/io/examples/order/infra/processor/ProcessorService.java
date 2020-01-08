package io.examples.order.infra.processor;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.event.ApplicationStartupEvent;
import io.vlingo.xoom.VlingoServer;
import io.vlingo.xoom.stepflow.StepFlow;
import io.vlingo.xoom.stepflow.ProcessorCreatedEvent;
import io.vlingo.xoom.stepflow.State;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class ProcessorService implements ApplicationEventListener<ApplicationStartupEvent> {

    private final State[] states;
    private StepFlow processor;

    public ProcessorService(State[] states) {
        this.states = states;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onApplicationEvent(ApplicationStartupEvent event) {
        processor = StepFlow.startWith(event.getSource()
                        .getApplicationContext()
                        .getBean(VlingoServer.class)
                        .getVlingoScene().getWorld().stage(), OrganizationProcessor.class,
                "OrganizationProcessor", Stream.of(Arrays.asList(states)).collect(Collectors.toList()));

        event.getSource().getApplicationContext()
                .publishEvent(new ProcessorCreatedEvent(processor, "organization"));
    }

    public StepFlow getProcessor() {
        return processor;
    }
}
