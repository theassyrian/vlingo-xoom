package io.examples.warehouse.infra.processor;

import io.vlingo.common.Completes;
import io.vlingo.xoom.stepflow.ProcessorActor;
import io.vlingo.xoom.stepflow.State;

import java.util.List;

public class WarehouseProcessorActor extends ProcessorActor {

    public WarehouseProcessorActor(List<State> states) {
        super(states);
    }

    @Override
    public Completes<String> getName() {
        return completes().with("Warehouse Processor");
    }
}
