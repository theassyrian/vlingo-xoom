package io.examples.order.domain;

import io.vlingo.xoom.processor.Event;

public class OrderEvent extends Event {

    public OrderEvent(String source, String target) {
        super(source, target);
    }
}
