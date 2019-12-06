package io.examples.warehouse.infra.processor;

import io.vlingo.xoom.processor.Processor;

public class WarehouseContext {

    private final Processor processor;

    public WarehouseContext(Processor processor) {
        this.processor = processor;
    }

    public Processor getProcessor() {
        return processor;
    }
}
