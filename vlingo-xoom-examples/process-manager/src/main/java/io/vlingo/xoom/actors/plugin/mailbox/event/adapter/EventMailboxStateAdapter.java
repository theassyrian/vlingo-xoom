package io.vlingo.xoom.actors.plugin.mailbox.event.adapter;

import io.vlingo.actors.Message;
import io.vlingo.xoom.actors.plugin.mailbox.event.BasicMailbox;
import io.vlingo.xoom.actors.plugin.mailbox.event.EventMailbox;
import io.vlingo.xoom.actors.plugin.mailbox.event.Processor;

/**
 * The {@link EventMailboxStateAdapter} provides storage implementation support for {@link EventMailbox} used to
 * define underlying storage implementations for {@link Processor}.
 *
 * @param <T> is a generic type that extends {@link Message}
 * @author Kenny Bastani
 */
public abstract class EventMailboxStateAdapter<T extends Message> implements BasicMailbox<T> {

    public abstract String getName();
}
