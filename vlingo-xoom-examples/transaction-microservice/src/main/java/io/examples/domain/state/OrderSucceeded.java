package io.examples.domain.state;

import io.vlingo.xoom.annotations.Resource;
import io.vlingo.xoom.processor.State;
import io.vlingo.xoom.processor.TransitionHandler;

@Resource
public class OrderSucceeded extends State<OrderSucceeded> {
    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[] {

        };
    }

    @Override
    public String getName() {
        return OrderStatus.ORDER_SUCCEEDED.name();
    }
}
