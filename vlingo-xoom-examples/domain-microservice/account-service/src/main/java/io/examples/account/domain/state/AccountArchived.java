package io.examples.account.domain.state;

import io.vlingo.common.Completes;
import io.vlingo.xoom.annotations.Resource;
import io.vlingo.xoom.processor.StateTransition;
import io.vlingo.xoom.processor.TransitionHandler;

@Resource
public class AccountArchived extends AccountState<AccountArchived> {

    public static final AccountStatus TYPE = AccountStatus.ACCOUNT_ARCHIVED;

    @Override
    public String getName() {
        return TYPE.name();
    }

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[]{
                TransitionHandler.handleFor(AccountArchived.class, AccountActivated.class)
                        .withPath(AccountActivated.TYPE.name())
                        .withTransition(this::toActivated)
        };
    }

    private Completes<StateTransition<AccountArchived, AccountActivated>> toActivated() {
        return StateTransition.transition(this, new AccountActivated())
                .onSuccess(log())
                .onError((transition, state) -> Completes.withFailure(transition))
                .build();
    }

    @Override
    public String toString() {
        return "AccountArchived{} " + super.toString();
    }
}