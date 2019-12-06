package io.examples.warehouse.domain.state;

import io.examples.warehouse.domain.model.WarehouseStatus;
import io.vlingo.xoom.processor.Transition;
import io.vlingo.xoom.processor.TransitionHandler;

import javax.inject.Singleton;

import static io.vlingo.xoom.processor.TransitionBuilder.from;
import static io.vlingo.xoom.processor.TransitionHandler.handle;

@Singleton
public class WarehouseCreated extends WarehouseState<WarehouseCreated> {

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[]{
                handle(from(this).to(this).then(Transition::logResult))
        };
    }

    @Override
    public String getName() {
        return WarehouseStatus.WAREHOUSE_CREATED.name();
    }
}
