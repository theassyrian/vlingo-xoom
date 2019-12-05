package io.examples.order.domain.state.processor;

import io.vlingo.common.Completes;
import io.vlingo.xoom.processor.ProcessorActor;
import io.vlingo.xoom.processor.State;

import java.util.List;

public class OrderProcessor extends ProcessorActor {

    public OrderProcessor(List<State> states) {
        super(states);
    }

    @Override
    public Completes<String> getName() {
        return completes().with("Order Processor");
    }
}
