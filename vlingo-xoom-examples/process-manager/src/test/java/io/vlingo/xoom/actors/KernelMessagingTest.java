package io.vlingo.xoom.actors;// Copyright © 2012-2018 Vaughn Vernon. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

import io.vlingo.actors.World;
import io.vlingo.xoom.actors.account.AccountProcessor;
import io.vlingo.xoom.actors.account.event.AccountEvent;
import io.vlingo.xoom.actors.account.state.*;
import io.vlingo.xoom.actors.processor.Processor;
import io.vlingo.xoom.actors.processor.StateTransition;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

public class KernelMessagingTest {
    private World world;

    @Test
    public void testAccountProcessorStarts() throws InterruptedException {

        final Processor test = Processor.startWith(
                world.stage(), // The parent actor's stage
                AccountProcessor.class, // The class of the processor actor implementation
                AccountProcessor.class.getCanonicalName()); // The name of the processor actor implementation

        AtomicReference<AccountState> state = new AtomicReference<>();
        state.set(new AccountCreated());

        applyEvent(test, state, new AccountPending());
        applyEvent(test, state, new AccountConfirmed());
        applyEvent(test, state, new AccountActivated());
        applyEvent(test, state, new AccountSuspended());
        applyEvent(test, state, new AccountActivated());
        applyEvent(test, state, new AccountArchived());
        applyEvent(test, state, new AccountActivated());

        Thread.sleep(2000L);

        test.shutDown();
    }

    private StateTransition applyEvent(Processor test, AtomicReference<AccountState> from, AccountState to) {
        return test.applyEvent(new AccountEvent(from.get(), to))
                .andThenConsume(t -> {
                    from.set((AccountState) t.getTo());
                    t.apply();
                }).await();
    }

    @Before
    public void setUp() {
        world = Boot.start("test-messaging");
    }

    @After
    public void tearDown() {
        world.terminate();
    }
}
