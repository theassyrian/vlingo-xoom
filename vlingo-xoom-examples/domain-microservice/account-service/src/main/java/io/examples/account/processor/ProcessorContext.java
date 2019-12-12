package io.examples.account.processor;

import io.micronaut.context.event.ApplicationEventListener;
import io.vlingo.xoom.processor.Processor;
import io.vlingo.xoom.processor.ProcessorCreatedEvent;
import io.vlingo.xoom.processor.SceneStartupEvent;
import io.vlingo.xoom.processor.State;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.Collections;

@Singleton
public class ProcessorContext implements ApplicationEventListener<SceneStartupEvent> {

    private final State[] states;
    private Processor processor;

    public ProcessorContext(State[] states) {
        this.states = states;
    }

    @Override
    public void onApplicationEvent(SceneStartupEvent event) {
        processor = Processor.startWith(event.getSource().getWorld().stage(),
                AccountProcessor.class, "AccountProcessor",
                Collections.singletonList(Arrays.asList(states)));

        event.getSource()
                .getApplicationContext()
                .publishEvent(new ProcessorCreatedEvent(processor, "account"));
    }

    public Processor getProcessor() {
        return processor;
    }
}
