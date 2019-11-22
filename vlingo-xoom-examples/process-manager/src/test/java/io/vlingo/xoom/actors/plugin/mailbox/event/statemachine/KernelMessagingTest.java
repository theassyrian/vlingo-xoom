// Copyright © 2012-2018 Vaughn Vernon. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.xoom.actors.plugin.mailbox.event.statemachine;

import io.vlingo.actors.World;
import io.vlingo.xoom.actors.Boot;
import io.vlingo.xoom.actors.plugin.mailbox.event.Processor;
import io.vlingo.xoom.actors.plugin.mailbox.event.adapter.InMemoryMailboxAdapter;
import io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.state.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class KernelMessagingTest {
    private World world;

    @Test
    public void testThatActorMessagesDeliver() {
        final Kernel test = world.actorFor(Kernel.class, KernelActor.class);

        test.registerStates(new AccountActivated(),
                new AccountConfirmed(),
                new AccountPending(),
                new AccountArchived(),
                new AccountSuspended(),
                new AccountCreated());

        test.getStates().andThenConsume((states) -> states.forEach(t -> System.out.println(t.toGraph())));

        /*
        Outputs:

            (ACCOUNT_ACTIVATED)-->([ACCOUNT_ARCHIVED, ACCOUNT_SUSPENDED])
            (ACCOUNT_CONFIRMED)-->([ACCOUNT_ACTIVATED])
            (ACCOUNT_PENDING)-->([ACCOUNT_CONFIRMED])
            (ACCOUNT_ARCHIVED)-->([ACCOUNT_ACTIVATED])
            (ACCOUNT_SUSPENDED)-->([ACCOUNT_ACTIVATED])
            (ACCOUNT_CREATED)-->([ACCOUNT_PENDING])

        */
    }

    @Test
    public void testAccountProcessorStarts() {

        final Processor test = Processor.startWith(
                world.stage(), // The parent actor's stage
                AccountProcessor.class, // The class of the processor actor implementation
                AccountProcessor.class.getCanonicalName(), // The name of the processor actor implementation
                new InMemoryMailboxAdapter<>(10)); // Choose an mailbox storage implementation

        // Log out the state transition pathways for the account processor
        test.getKernel().andThenConsume(kernel -> kernel.getStates()
                .andThenConsume((states) ->
                        states.forEach(t -> System.out.println(t.toGraph()))));

        test.shutDown();
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
