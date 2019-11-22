package io.vlingo.xoom.actors.plugin.mailbox.event;

import io.vlingo.actors.Message;
import io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.Kernel;

import java.util.Queue;

/**
 * An {@link EventMailbox} is the base mailbox implementation used for event processing by a {@link Kernel}'s
 * {@link Processor}.
 *
 * @param <T> is a generic type that extends {@link Message}
 * @author Kenny Bastani
 */
public interface EventMailbox<T extends Message> extends BasicMailbox<T> {

    @Override
    <X extends Queue<T>> X getQueue();
}
