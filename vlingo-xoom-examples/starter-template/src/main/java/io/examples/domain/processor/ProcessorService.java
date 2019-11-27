package io.examples.domain.processor;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.event.ApplicationStartupEvent;
import io.vlingo.xoom.VlingoServer;
import io.vlingo.xoom.processor.Processor;
import io.vlingo.xoom.processor.ProcessorStartupEvent;
import io.vlingo.xoom.processor.State;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class ProcessorService implements ApplicationEventListener<ApplicationStartupEvent> {

    private final State[] states;
    private Processor processor;

    public ProcessorService(State[] states) {
        this.states = states;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onApplicationEvent(ApplicationStartupEvent event) {
        processor = Processor.startWith(event.getSource()
                        .getApplicationContext()
                        .getBean(VlingoServer.class)
                        .getVlingoScene().getWorld().stage(), OrganizationProcessor.class,
                "OrganizationProcessor", Stream.of(Arrays.asList(states)).collect(Collectors.toList()));

        event.getSource().getApplicationContext()
                .publishEvent(new ProcessorStartupEvent(processor));
    }

    public Processor getProcessor() {
        return processor;
    }
}
