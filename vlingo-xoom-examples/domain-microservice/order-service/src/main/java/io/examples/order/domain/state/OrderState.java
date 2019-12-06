package io.examples.order.domain.state;

import io.vlingo.xoom.processor.State;

public abstract class OrderState<T extends OrderState> extends State<T> {
}
