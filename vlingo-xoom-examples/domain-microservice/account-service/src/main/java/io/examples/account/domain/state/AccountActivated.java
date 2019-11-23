package io.examples.account.domain.state;

import io.examples.account.domain.Account;
import io.examples.account.processor.AccountProcessor;
import io.vlingo.common.Completes;
import io.vlingo.xoom.annotations.Resource;
import io.vlingo.xoom.processor.Processor;
import io.vlingo.xoom.processor.State;
import io.vlingo.xoom.processor.StateTransition;
import io.vlingo.xoom.processor.TransitionHandler;

import static io.examples.account.domain.state.AccountStatus.ACCOUNT_NUMBER_UPDATED;
import static io.vlingo.xoom.processor.StateTransition.with;
import static io.vlingo.xoom.processor.TransitionHandler.handleFor;

/**
 * The {@link AccountState} resource is a {@link State} implementation that defines event handlers
 * for transitioning between {@link AccountStatus} belonging to an {@link Account}. This class
 * will be automatically registered with a {@link Processor} in the Micronaut application context.
 *
 * @author Kenny Bastani
 * @see AccountProcessor
 */
@Resource
public class AccountActivated extends AccountState<AccountActivated> {

    public static final AccountStatus TYPE = AccountStatus.ACCOUNT_ACTIVATED;

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[]{
                handleFor(AccountActivated.class, AccountArchived.class)
                        .withPath(AccountArchived.TYPE.name())
                        .withTransition(this::handleArchived),
                handleFor(AccountActivated.class, AccountSuspended.class)
                        .withPath(AccountSuspended.TYPE.name())
                        .withTransition(this::handleSuspended),
                handleFor(AccountActivated.class, AccountActivated.class)
                        .withPath(AccountActivated.TYPE.name() +
                                "::" + ACCOUNT_NUMBER_UPDATED.name())
                        .withTransition(this::handleAccountUpdated)
        };
    }

    private Completes<StateTransition<AccountActivated, AccountArchived>> handleArchived() {
        return with(this, new AccountArchived())
                .onSuccess(log()) // This handler can be used to notify external systems
                .onError(log()) // This handler can be used to remedy a failure
                .build();
    }

    private Completes<StateTransition<AccountActivated, AccountSuspended>> handleSuspended() {
        return with(this, new AccountSuspended())
                .onSuccess(log())
                .onError(log())
                .build();
    }

    /**
     * This handler is an example of handling a sub-state event where the logical state of the
     * {@link Account} should not change. For example, an {@link Account} should only be allowed
     * to change its accountNumber if the {@link AccountState} is {@link AccountActivated}. This
     * is a valid transition, as the state of the entity has changed but the logical state remains
     * the same.
     */
    private Completes<StateTransition<AccountActivated, AccountActivated>> handleAccountUpdated() {
        return with(this, new AccountActivated())
                .onSuccess(log(ACCOUNT_NUMBER_UPDATED.name())) // Log the sub-state transition
                .onError(log(ACCOUNT_NUMBER_UPDATED.name()))
                .build();
    }

    @Override
    public String getName() {
        return TYPE.name();
    }
}
