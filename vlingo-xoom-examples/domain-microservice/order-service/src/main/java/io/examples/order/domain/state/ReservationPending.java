package io.examples.order.domain.state;

import io.vlingo.xoom.processor.State;
import io.vlingo.xoom.processor.Transition;
import io.vlingo.xoom.processor.TransitionHandler;

import javax.inject.Singleton;

import static io.vlingo.xoom.processor.TransitionBuilder.from;
import static io.vlingo.xoom.processor.TransitionHandler.handle;

@Singleton
public class ReservationPending extends State<ReservationPending> {

    private final ReservationFailed reservationFailed;
    private final ReservationSucceeded reservationSucceeded;

    public ReservationPending(ReservationFailed reservationFailed,
                              ReservationSucceeded reservationSucceeded) {
        this.reservationFailed = reservationFailed;
        this.reservationSucceeded = reservationSucceeded;
    }

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[]{
                handle(from(this).to(this)
                        .then(Transition::logResult))
                        .withAddress(OrderStatus.INVENTORY_RESERVED.name()),
                handle(from(this).to(reservationSucceeded)
                        .then(Transition::logResult)),
                handle(from(this).to(reservationFailed)
                        .then(Transition::logResult))
        };
    }

    @Override
    public String getName() {
        return OrderStatus.RESERVATION_PENDING.name();
    }
}
