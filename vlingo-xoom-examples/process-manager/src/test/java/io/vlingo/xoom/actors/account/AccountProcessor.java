package io.vlingo.xoom.actors.account;

import io.vlingo.common.Completes;
import io.vlingo.xoom.actors.account.state.*;
import io.vlingo.xoom.actors.processor.Kernel;
import io.vlingo.xoom.actors.processor.Processor;
import io.vlingo.xoom.actors.processor.ProcessorActor;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The {@link AccountProcessor} creates a {@link ProcessorActor} that compiles a {@link Kernel} for the list of
 * provided {@link AccountState < State >} implementations. All state transitions are driven through fluent models
 * that do not delegate control to a configuration class or DSL. The {@link Processor} understands how to stitch
 * together the graph of transitions by making API calls to its attached {@link Kernel} implementation.
 *
 * @author Kenny Bastani
 */
public class AccountProcessor extends ProcessorActor {

    public AccountProcessor() {
        super(Stream.of(new AccountActivated(),
                new AccountConfirmed(),
                new AccountPending(),
                new AccountArchived(),
                new AccountSuspended(),
                new AccountCreated()).collect(Collectors.toList()));
    }

    @Override
    public Completes<String> getName() {
        return Completes.withSuccess("AccountProcessor");
    }
}
