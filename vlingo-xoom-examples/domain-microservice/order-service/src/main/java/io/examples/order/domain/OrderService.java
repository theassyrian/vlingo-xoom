package io.examples.order.domain;

import io.examples.order.domain.state.OrderStatus;
import io.examples.order.domain.state.processor.ProcessorService;
import io.examples.order.infra.repository.OrderRepository;
import io.vlingo.common.Completes;

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

    public Completes<Order> defineOrder(Order orderDefinition) {
        // Remove the identity from the shipping address and save the order
        return Completes.withSuccess(orderDefinition)
                .andThen(order -> order.sendEvent(processorService.getProcessor(),
                        OrderStatus.ACCOUNT_CONNECTED))
                .andThen(order -> order.sendEvent(processorService.getProcessor(),
                        OrderStatus.RESERVATION_PENDING))
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
