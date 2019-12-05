package io.examples.order.domain;

import io.vlingo.xoom.processor.Event;

public class OrganizationEvent extends Event {

    public OrganizationEvent(String source, String target) {
        super(source, target);
    }
}
