package io.vlingo.xoom.processor;

import io.micronaut.context.event.ApplicationEvent;

public class ProcessorStartupEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ProcessorStartupEvent(Processor source) {
        super(source);
    }

    @Override
    public Processor getSource() {
        return (Processor)super.getSource();
    }
}
