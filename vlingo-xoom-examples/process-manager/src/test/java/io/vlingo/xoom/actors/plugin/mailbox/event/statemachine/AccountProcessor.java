package io.vlingo.xoom.actors.plugin.mailbox.event.statemachine;

import io.vlingo.xoom.actors.plugin.mailbox.event.Processor;
import io.vlingo.xoom.actors.plugin.mailbox.event.ProcessorActor;
import io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.state.*;

import java.util.List;

/**
 * The {@link AccountProcessor} creates a {@link ProcessorActor} that compiles a {@link Kernel} for the list of
 * provided {@link AccountState<State>} implementations. All state transitions are driven through fluent models
 * that do not delegate control to a configuration class or DSL. The {@link Processor} understands how to stitch
 * together the graph of transitions by making API calls to its attached {@link Kernel} implementation.
 *
 * @author Kenny Bastani
 */
public class AccountProcessor extends ProcessorActor {

    public AccountProcessor() {
        super(List.of(new AccountActivated(),
                new AccountConfirmed(),
                new AccountPending(),
                new AccountArchived(),
                new AccountSuspended(),
                new AccountCreated()));
    }
}
