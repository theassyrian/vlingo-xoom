package io.examples.warehouse.infra.processor;

import io.vlingo.xoom.stepflow.StepFlow;

public class WarehouseContext {

    private final StepFlow processor;

    public WarehouseContext(StepFlow processor) {
        this.processor = processor;
    }

    public StepFlow getProcessor() {
        return processor;
    }
}
