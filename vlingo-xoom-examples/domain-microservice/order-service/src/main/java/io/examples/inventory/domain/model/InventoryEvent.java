package io.examples.inventory.domain.model;

import io.vlingo.xoom.processor.Event;

public class InventoryEvent extends Event {

    public InventoryEvent(String source, String target) {
        super(source, target);
    }
}
