package io.examples.order.infra.processor;

import io.vlingo.xoom.processor.Processor;

public class OrderContext {
    private final Processor processor;

    public OrderContext(Processor processor) {
        this.processor = processor;
    }

    public Processor getProcessor() {
        return processor;
    }
}
