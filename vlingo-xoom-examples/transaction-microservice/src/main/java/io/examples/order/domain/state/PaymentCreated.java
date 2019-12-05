package io.examples.order.domain.state;

import io.vlingo.xoom.processor.State;
import io.vlingo.xoom.processor.Transition;
import io.vlingo.xoom.processor.TransitionHandler;

import javax.inject.Singleton;

import static io.vlingo.xoom.processor.TransitionBuilder.from;
import static io.vlingo.xoom.processor.TransitionHandler.handle;

@Singleton
public class PaymentCreated extends State<PaymentCreated> {

    private final PaymentPending paymentPending;

    public PaymentCreated(PaymentPending paymentPending) {
        this.paymentPending = paymentPending;
    }

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[] {
                handle(from(this).to(paymentPending).then(Transition::logResult))
        };
    }

    @Override
    public String getName() {
        return OrderStatus.PAYMENT_CREATED.name();
    }
}
