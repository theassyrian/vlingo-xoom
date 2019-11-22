package io.vlingo.xoom.actors.plugin.mailbox.event.statemachine;

import io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.state.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class StateTest {

    @Test
    public void testMapStateTransitions() {

        AccountActivated accountActivated = new AccountActivated();


        Map<String, Set<String>> actual = accountActivated.toMap();

        Map<String, Set<String>> expected = new HashMap<>();
        expected.put("ACCOUNT_ACTIVATED", new HashSet<>(List.of("ACCOUNT_ARCHIVED", "ACCOUNT_SUSPENDED")));

        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);

        System.out.println(new AccountCreated().toGraph());
        System.out.println(new AccountPending().toGraph());
        System.out.println(new AccountConfirmed().toGraph());
        System.out.println(new AccountActivated().toGraph());
        System.out.println(new AccountSuspended().toGraph());
        System.out.println(new AccountArchived().toGraph());

        System.out.println(new AccountCreated().toString());
        System.out.println(new AccountPending().toString());
        System.out.println(new AccountConfirmed().toString());
        System.out.println(new AccountActivated().toString());
        System.out.println(new AccountSuspended().toString());
        System.out.println(new AccountArchived().toString());
    }
}