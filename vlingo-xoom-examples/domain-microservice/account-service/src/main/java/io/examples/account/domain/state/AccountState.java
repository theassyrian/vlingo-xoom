package io.examples.account.domain.state;


import io.vlingo.actors.Logger;
import io.vlingo.common.Completes;
import io.vlingo.xoom.processor.CompletesState;
import io.vlingo.xoom.processor.State;

public abstract class AccountState<T extends State> extends State<T> {
    final static Logger log = Logger.basicLogger();

    public AccountState() {
    }

    public AccountStatus getType() {
        return AccountStatus.valueOf(getName());
    }

    @Override
    public String toString() {
        return "AccountState{} " + super.toString();
    }


    public <T1 extends AccountState, R1 extends AccountState> CompletesState<T1, R1> log() {
        return (transition, state) -> {
            log.info(state.getVersion() + ": [" + transition.getSourceName() +
                    "] to [" + transition.getTargetName() + "]");
            return Completes.withSuccess(transition);
        };

    }
}
