package io.examples.account.domain.state;

import io.examples.account.domain.Account;
import io.examples.account.processor.AccountProcessor;
import io.vlingo.common.Completes;
import io.vlingo.xoom.annotations.Resource;
import io.vlingo.xoom.processor.Processor;
import io.vlingo.xoom.processor.State;
import io.vlingo.xoom.processor.StateTransition;
import io.vlingo.xoom.processor.TransitionHandler;

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
public class AccountArchived extends AccountState<AccountArchived> {

    public static final AccountStatus TYPE = AccountStatus.ACCOUNT_ARCHIVED;

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[]{
                handleFor(AccountArchived.class, AccountActivated.class)
                        .withPath(AccountActivated.TYPE.name())
                        .withTransition(this::toActivated)
        };
    }

    private Completes<StateTransition<AccountArchived, AccountActivated>> toActivated() {
        return with(this, new AccountActivated())
                .onSuccess(log())
                .onError(log())
                .build();
    }

    @Override
    public String getName() {
        return TYPE.name();
    }
}