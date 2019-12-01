package io.examples.domain.state;

import io.vlingo.xoom.annotations.Resource;
import io.vlingo.xoom.processor.State;
import io.vlingo.xoom.processor.Transition;
import io.vlingo.xoom.processor.TransitionHandler;

import static io.vlingo.xoom.processor.TransitionBuilder.from;
import static io.vlingo.xoom.processor.TransitionHandler.handle;

@Resource
public class ReservationPending extends State<ReservationPending> {
    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[]{
                handle(from(this).to(this)
                        .then(Transition::logResult))
                        .withAddress(OrderStatus.INVENTORY_RESERVED.name()),
                handle(from(this).to(new ReservationSucceeded())
                        .then(Transition::logResult)),
                handle(from(this).to(new ReservationFailed())
                        .then(Transition::logResult))
        };
    }

    @Override
    public String getName() {
        return OrderStatus.RESERVATION_PENDING.name();
    }
}
