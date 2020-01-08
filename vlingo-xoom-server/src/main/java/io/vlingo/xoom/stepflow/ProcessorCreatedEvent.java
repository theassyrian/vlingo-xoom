package io.vlingo.xoom.stepflow;

import io.micronaut.context.event.ApplicationEvent;

public class ProcessorCreatedEvent extends ApplicationEvent {
    private final StepFlow source;
    private final String processorName;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ProcessorCreatedEvent(StepFlow source, String processorName) {
        super(source);
        this.source = source;
        this.processorName = processorName;
    }

    @Override
    public StepFlow getSource() {
        return source;
    }

    public String getProcessorName() {
        return processorName;
    }
}
