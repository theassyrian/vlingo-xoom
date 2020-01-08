package io.examples.order.infra.processor;

import io.vlingo.xoom.stepflow.ProcessorActor;
import io.vlingo.xoom.stepflow.State;

import java.util.List;

public class OrganizationProcessor extends ProcessorActor {

    public OrganizationProcessor(List<State> states) {
        super(states);
    }
}
