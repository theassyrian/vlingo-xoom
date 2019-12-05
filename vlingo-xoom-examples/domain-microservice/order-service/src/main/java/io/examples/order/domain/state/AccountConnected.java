package io.examples.order.domain.state;

import io.vlingo.xoom.processor.State;
import io.vlingo.xoom.processor.Transition;
import io.vlingo.xoom.processor.TransitionHandler;

import javax.inject.Singleton;

import static io.vlingo.xoom.processor.TransitionBuilder.from;
import static io.vlingo.xoom.processor.TransitionHandler.handle;

/**
 * The {@link AccountConnected} state transitions from the {@link OrderCreated} state. This state automatically
 * transitions from {@link AccountConnected} to {@link ReservationPending}.
 *
 * @author Kenny Bastani
 */
@Singleton
public class AccountConnected extends State<AccountConnected> {

    private final ReservationPending reservationPending;

    public AccountConnected(ReservationPending reservationPending) {
        this.reservationPending = reservationPending;
    }

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[]{
                handle(from(this).to(reservationPending)
                        .then(Transition::logResult))
        };
    }

    @Override
    public String getName() {
        return OrderStatus.ACCOUNT_CONNECTED.name();
    }
}
