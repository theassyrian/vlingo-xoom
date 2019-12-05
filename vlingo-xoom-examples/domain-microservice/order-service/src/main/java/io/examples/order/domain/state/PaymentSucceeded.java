package io.examples.order.domain.state;

import io.vlingo.xoom.processor.State;
import io.vlingo.xoom.processor.Transition;
import io.vlingo.xoom.processor.TransitionHandler;

import javax.inject.Singleton;

import static io.vlingo.xoom.processor.TransitionBuilder.from;
import static io.vlingo.xoom.processor.TransitionHandler.handle;

@Singleton
public class PaymentSucceeded extends State<PaymentSucceeded> {

    private final OrderSucceeded orderSucceeded;

    public PaymentSucceeded(OrderSucceeded orderSucceeded) {
        this.orderSucceeded = orderSucceeded;
    }

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[] {
            handle(from(this).to(orderSucceeded).then(Transition::logResult))
        };
    }

    @Override
    public String getName() {
        return OrderStatus.PAYMENT_SUCCEEDED.name();
    }
}
