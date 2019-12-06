package io.examples.order.domain.state;

import io.vlingo.xoom.processor.TransitionHandler;

import javax.inject.Singleton;

@Singleton
public class OrderFailed extends OrderState<OrderFailed> {

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[] {
        };
    }

    @Override
    public String getName() {
        return OrderStatus.ORDER_FAILED.name();
    }
}
