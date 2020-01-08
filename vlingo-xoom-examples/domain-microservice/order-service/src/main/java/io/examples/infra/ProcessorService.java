package io.examples.infra;

import io.examples.inventory.domain.state.InventoryState;
import io.examples.inventory.infra.processor.InventoryContext;
import io.examples.inventory.infra.processor.InventoryProcessorActor;
import io.examples.order.domain.state.OrderState;
import io.examples.order.infra.processor.OrderContext;
import io.examples.order.infra.processor.OrderProcessorActor;
import io.examples.warehouse.domain.state.WarehouseState;
import io.examples.warehouse.infra.processor.WarehouseContext;
import io.examples.warehouse.infra.processor.WarehouseProcessorActor;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.event.ApplicationStartupEvent;
import io.vlingo.xoom.VlingoServer;
import io.vlingo.xoom.stepflow.StepFlow;
import io.vlingo.xoom.stepflow.ProcessorActor;
import io.vlingo.xoom.stepflow.ProcessorCreatedEvent;
import io.vlingo.xoom.stepflow.State;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class ProcessorService implements ApplicationEventListener<ApplicationStartupEvent> {

    private final List<OrderState> orderStates;
    private final List<WarehouseState> warehouseStates;
    private final List<InventoryState> inventoryStates;
    private OrderContext orderProcessor;
    private WarehouseContext warehouseContext;
    private InventoryContext inventoryContext;

    public ProcessorService(List<OrderState> orderStates,
                            List<WarehouseState> warehouseStates,
                            List<InventoryState> inventoryStates) {
        this.orderStates = orderStates;
        this.warehouseStates = warehouseStates;
        this.inventoryStates = inventoryStates;
    }

    @Override
    public void onApplicationEvent(ApplicationStartupEvent event) {
        // Create a new order processor
        orderProcessor = new OrderContext(defineProcessor(event,
                OrderProcessorActor.class,
                orderStates.stream().map(state -> (State) state).collect(Collectors.toList()),
                "order"));

        // Create a new warehouse processor
        warehouseContext = new WarehouseContext(defineProcessor(event,
                WarehouseProcessorActor.class,
                warehouseStates.stream().map(state -> (State) state).collect(Collectors.toList()),
                "warehouse"));

        // Create a new inventory processor
        inventoryContext = new InventoryContext(defineProcessor(event,
                InventoryProcessorActor.class,
                inventoryStates.stream().map(state -> (State) state).collect(Collectors.toList()),
                "inventory"));
    }

    private <P extends StepFlow, A extends ProcessorActor> StepFlow defineProcessor(ApplicationStartupEvent event,
                                                                                    Class<A> actorClass,
                                                                                    List<State> states,
                                                                                    String processorName) {
        StepFlow processor = StepFlow.startWith(event.getSource()
                .getApplicationContext()
                .getBean(VlingoServer.class)
                .getVlingoScene()
                .getWorld()
                .stage(), actorClass, StepFlow.class, processorName, Collections.singletonList(states));

        event.getSource()
                .getApplicationContext()
                .publishEvent(new ProcessorCreatedEvent(processor, processorName));

        return processor;
    }

    public OrderContext getOrderContext() {
        return orderProcessor;
    }

    public WarehouseContext getWarehouseContext() {
        return warehouseContext;
    }

    public InventoryContext getInventoryContext() {
        return inventoryContext;
    }
}
