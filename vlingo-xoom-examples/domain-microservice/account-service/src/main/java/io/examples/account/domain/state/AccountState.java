package io.examples.account.domain.state;


import io.examples.account.domain.Account;
import io.examples.account.processor.AccountProcessor;
import io.vlingo.actors.Logger;
import io.vlingo.common.Completes;
import io.vlingo.xoom.processor.CompletesState;
import io.vlingo.xoom.processor.Processor;
import io.vlingo.xoom.processor.State;

/**
 * The {@link AccountState} resource is a {@link State} implementation that defines event handlers
 * for transitioning between {@link AccountStatus} belonging to an {@link Account}. This class
 * will be automatically registered with a {@link Processor} in the Micronaut application context.
 *
 * @author Kenny Bastani
 * @see AccountProcessor
 */
public abstract class AccountState<T extends State> extends State<T> {
    private final static Logger log = Logger.basicLogger();

    public AccountState() {
    }

    /**
     * A helper function that logs out the transitions of {@link AccountState}.
     */
    <T1 extends AccountState, R1 extends AccountState> CompletesState<T1, R1> log() {
        return (transition, state) -> {
            log.info(state.getVersion() + ": [" + transition.getSourceName() + "] to ["
                    + transition.getTargetName() + "]");
            return Completes.withSuccess(transition);
        };
    }

    <T1 extends AccountState, R1 extends AccountState> CompletesState<T1, R1> log(String subtype) {
        return (transition, state) -> {
            log.info(state.getVersion() + ": [" + transition.getSourceName() + "] to ["
                    + transition.getTargetName() + "::" + subtype + "]");
            return Completes.withSuccess(transition);
        };
    }

    public AccountStatus getType() {
        return AccountStatus.valueOf(getName());
    }

    @Override
    public String toString() {
        return "AccountState{} " + super.toString();
    }

}
