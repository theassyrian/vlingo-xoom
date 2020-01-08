package io.examples.order.infra.processor;

import io.vlingo.xoom.stepflow.StepFlow;

public class OrderContext {
    private final StepFlow processor;

    public OrderContext(StepFlow processor) {
        this.processor = processor;
    }

    public StepFlow getProcessor() {
        return processor;
    }
}
