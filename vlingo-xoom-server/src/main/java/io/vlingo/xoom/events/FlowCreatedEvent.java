package io.vlingo.xoom.events;

import io.micronaut.context.event.ApplicationEvent;
import io.vlingo.xoom.stepflow.StepFlow;

public class FlowCreatedEvent extends ApplicationEvent {
    private final StepFlow source;
    private final String processorName;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public FlowCreatedEvent(StepFlow source, String processorName) {
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
