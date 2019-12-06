package io.examples.order.application;

import io.examples.order.domain.Order;
import io.examples.order.domain.state.OrderStatus;
import io.examples.infra.ProcessorService;
import io.examples.order.infra.repository.OrderRepository;
import io.vlingo.common.Completes;
import io.vlingo.xoom.processor.Processor;

import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProcessorService processorService;

    public OrderService(OrderRepository orderRepository, Provider<ProcessorService> processorService) {
        this.orderRepository = orderRepository;
        this.processorService = processorService.get();
    }

    /**
     * Here we define an {@link Order} and begin the order workflow process by moving along the state of the
     * order from ORDER_CREATED to RESERVATION_PENDING.
     *
     * @param orderDefinition is the definition of the order that we will be creating
     * @return a completes publisher that will execute as the response is returned to the HTTP resource consumer
     */
    public Completes<Order> defineOrder(Order orderDefinition) {
        final Processor processor = processorService.getOrderContext().getProcessor();
        return Completes.withSuccess(orderDefinition)
                .andThen(order -> order.sendEvent(processor, OrderStatus.ACCOUNT_CONNECTED))
                .andThen(order -> order.sendEvent(processor, OrderStatus.RESERVATION_PENDING))
                .andThenConsume(orderRepository::save)
                .andThenConsume(order -> queryOrder(order.getId()))
                .otherwise(order -> {
                    throw new RuntimeException("Could not define the order: " + order.toString());
                });
    }

    public Completes<Order> queryOrder(Long id) {
        return orderRepository.findById(id).map(Completes::withSuccess)
                .orElseThrow(() -> new RuntimeException("Could not find order"));
    }
}
