package io.examples.domain.processor;

import io.vlingo.xoom.processor.ProcessorActor;
import io.vlingo.xoom.processor.State;

import java.util.List;

public class OrganizationProcessor extends ProcessorActor {

    public OrganizationProcessor(List<State> states) {
        super(states);
    }
}