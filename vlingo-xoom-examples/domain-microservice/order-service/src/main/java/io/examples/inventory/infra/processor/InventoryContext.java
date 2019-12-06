package io.examples.inventory.infra.processor;

import io.vlingo.xoom.processor.Processor;

public class InventoryContext {

    private final Processor processor;

    public InventoryContext(Processor processor) {
        this.processor = processor;
    }

    public Processor getProcessor() {
        return processor;
    }
}
