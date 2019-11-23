package io.vlingo.xoom.processor;

import io.micronaut.context.event.ApplicationEvent;

public class ProcessorStartupEvent extends ApplicationEvent {
    private final Processor source;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ProcessorStartupEvent(Processor source) {
        super(source);
        this.source = source;
    }

    @Override
    public Processor getSource() {
        return source;
    }
}
