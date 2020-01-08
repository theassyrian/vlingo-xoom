package io.examples.inventory.infra.processor;

import io.vlingo.xoom.stepflow.StepFlow;

public class InventoryContext {

    private final StepFlow processor;

    public InventoryContext(StepFlow processor) {
        this.processor = processor;
    }

    public StepFlow getProcessor() {
        return processor;
    }
}
