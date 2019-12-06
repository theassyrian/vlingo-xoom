package io.vlingo.xoom.processor;

import io.micronaut.context.event.ApplicationEvent;

public class ProcessorCreatedEvent extends ApplicationEvent {
    private final Processor source;
    private final String processorName;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ProcessorCreatedEvent(Processor source, String processorName) {
        super(source);
        this.source = source;
        this.processorName = processorName;
    }

    @Override
    public Processor getSource() {
        return source;
    }

    public String getProcessorName() {
        return processorName;
    }
}
