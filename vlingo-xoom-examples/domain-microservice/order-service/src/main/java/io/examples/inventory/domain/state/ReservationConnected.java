package io.examples.inventory.domain.state;

import io.examples.inventory.domain.model.InventoryStatus;
import io.vlingo.xoom.processor.Transition;
import io.vlingo.xoom.processor.TransitionHandler;

import javax.inject.Provider;
import javax.inject.Singleton;

import static io.vlingo.xoom.processor.TransitionBuilder.from;
import static io.vlingo.xoom.processor.TransitionHandler.handle;

@Singleton
public class ReservationConnected extends InventoryState<ReservationConnected> {

    private final Provider<InventoryReserved> inventoryReserved;

    public ReservationConnected(Provider<InventoryReserved> inventoryReserved) {
        this.inventoryReserved = inventoryReserved;
    }

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[] {
                handle(from(this).to(inventoryReserved.get()).then(Transition::logResult))
        };
    }

    @Override
    public String getName() {
        return InventoryStatus.RESERVATION_CONNECTED.name();
    }
}
