package io.examples.warehouse.domain.model;

import io.vlingo.xoom.processor.Event;

public class WarehouseEvent extends Event {

    public WarehouseEvent(String source, String target) {
        super(source, target);
    }
}
