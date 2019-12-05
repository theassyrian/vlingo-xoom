package io.examples.order.domain.state;

import io.vlingo.xoom.processor.State;
import io.vlingo.xoom.processor.Transition;
import io.vlingo.xoom.processor.TransitionHandler;

import javax.inject.Singleton;

import static io.vlingo.xoom.processor.TransitionBuilder.from;
import static io.vlingo.xoom.processor.TransitionHandler.handle;

@Singleton
public class PaymentPending extends State<PaymentPending> {

    private final PaymentSucceeded paymentSucceeded;
    private final PaymentFailed paymentFailed;

    public PaymentPending(PaymentSucceeded paymentSucceeded, PaymentFailed paymentFailed) {
        this.paymentSucceeded = paymentSucceeded;
        this.paymentFailed = paymentFailed;
    }

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[] {
                handle(from(this).to(paymentSucceeded).then(Transition::logResult)),
                handle(from(this).to(paymentFailed).then(Transition::logResult))
        };
    }

    @Override
    public String getName() {
        return OrderStatus.PAYMENT_PENDING.name();
    }
}
