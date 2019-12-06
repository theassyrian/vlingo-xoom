package io.examples.inventory.infra.processor;

import io.vlingo.common.Completes;
import io.vlingo.xoom.processor.ProcessorActor;
import io.vlingo.xoom.processor.State;

import java.util.List;

public class InventoryProcessorActor extends ProcessorActor {

    public InventoryProcessorActor(List<State> states) {
        super(states);
    }

    @Override
    public Completes<String> getName() {
        return completes().with("Inventory Processor");
    }
}
