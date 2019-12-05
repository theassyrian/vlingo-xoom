package io.examples.account.processor;

import io.micronaut.context.event.ApplicationEventListener;
import io.vlingo.xoom.processor.Processor;
import io.vlingo.xoom.processor.ProcessorStartupEvent;
import io.vlingo.xoom.processor.SceneStartupEvent;
import io.vlingo.xoom.processor.State;

import javax.inject.Singleton;

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
                java.util.List.of(java.util.List.of(states)));

        event.getSource()
                .getApplicationContext()
                .publishEvent(new ProcessorStartupEvent(processor));
    }

    public Processor getProcessor() {
        return processor;
    }
}
