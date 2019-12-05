package io.examples.order.domain.state.processor;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.event.ApplicationStartupEvent;
import io.vlingo.xoom.VlingoServer;
import io.vlingo.xoom.processor.Processor;
import io.vlingo.xoom.processor.ProcessorStartupEvent;
import io.vlingo.xoom.processor.State;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;

@Singleton
public class ProcessorService implements ApplicationEventListener<ApplicationStartupEvent> {

    private final List<State> providerStates;
    private Processor processor;

    public ProcessorService(List<State> states) {
        this.providerStates = states;
    }

    @Override
    public void onApplicationEvent(ApplicationStartupEvent event) {
        processor = Processor.startWith(event.getSource().getApplicationContext().getBean(VlingoServer.class)
                        .getVlingoScene().getWorld().stage(), OrderProcessor.class, "OrderProcessor",
                Collections.singletonList(providerStates));

        event.getSource().getApplicationContext().publishEvent(new ProcessorStartupEvent(processor));
    }

    public Processor getProcessor() {
        return processor;
    }
}
