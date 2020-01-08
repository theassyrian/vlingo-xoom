package io.examples.order.infra.processor;

import io.vlingo.common.Completes;
import io.vlingo.xoom.stepflow.ProcessorActor;
import io.vlingo.xoom.stepflow.State;

import java.util.List;

public class OrderProcessorActor extends ProcessorActor {

    public OrderProcessorActor(List<State> states) {
        super(states);
    }

    @Override
    public Completes<String> getName() {
        return completes().with("Order Processor");
    }
}
