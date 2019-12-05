package io.examples.order.domain.state;

import io.examples.account.application.AccountContext;
import io.examples.account.domain.model.AccountQuery;
import io.examples.order.domain.Address;
import io.examples.order.domain.Order;
import io.reactivex.Single;
import io.vlingo.xoom.processor.State;
import io.vlingo.xoom.processor.Transition;
import io.vlingo.xoom.processor.TransitionHandler;

import javax.inject.Provider;
import javax.inject.Singleton;

import static io.vlingo.xoom.processor.TransitionBuilder.from;
import static io.vlingo.xoom.processor.TransitionHandler.handle;

@Singleton
public class OrderCreated extends State<OrderCreated> {

    private final AccountContext accountContext;
    private final AccountConnected accountConnected;

    public OrderCreated(Provider<AccountContext> accountContext, AccountConnected accountConnected) {
        this.accountContext = accountContext.get();
        this.accountConnected = accountConnected;
    }

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[]{
                handle(from(this).to(accountConnected).on(Order.class)
                        .then(this::connectAccount)
                        .then(Transition::logResult))
        };
    }

    /**
     * This is the function command for handling the {@link OrderCreated} event. This function will call the
     * account service to retrieve a shipping address and add it to the order.
     *
     * @param order is the definition of the {@link Order} containing the context for this event handler
     * @return the updated {@link Order} definition to be persisted
     */
    private Order connectAccount(Order order) {

        // Retrieve the function to query the account context
        Single<AccountQuery> single = accountContext.getAccount()
                .query(order.getAccountId())
                .await();

        // Issue a blocking get to call the account service and retrieve the account
        AccountQuery accountQuery = single.blockingGet();

        // Set the shipping address on the order sourced from the account query or throw an error
        order.setShippingAddress(Address.translateFrom(accountQuery.getAddresses()
                .stream()
                .filter(address -> address.getType().name().equals(Address.AddressType.SHIPPING.name()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("The account does not have a shipping address"))));

        return order;
    }

    @Override
    public String getName() {
        return OrderStatus.ORDER_CREATED.name();
    }
}
