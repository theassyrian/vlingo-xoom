package io.vlingo.xoom.actors.plugin.mailbox.event;

import io.vlingo.actors.Actor;
import io.vlingo.actors.Mailbox;
import io.vlingo.actors.Message;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public interface BasicMailbox<T extends Message> extends Mailbox {
    final AtomicBoolean closed = new AtomicBoolean(false);
    final AtomicBoolean delivering = new AtomicBoolean(false);
    final AtomicReference<Stack<List<Class<?>>>> suspendedOverrides = new AtomicReference<>(new Stack<>());

    <X extends Queue<T>> X getQueue();

    @Override
    public default void run() {
        throw new UnsupportedOperationException("BasicMailbox does not support this operation.");
    }

    @Override
    public default void close() {
        closed.set(true);
    }

    @Override
    public default boolean isClosed() {
        return closed.get();
    }

    @Override
    public default boolean isDelivering() {
        return delivering.get();
    }

    @Override
    public default void resume(final String name) {
        if (!suspendedOverrides.get().empty()) {
            suspendedOverrides.get().pop();
        }
        deliverAll();
    }

    public default void publish(final T message) {
        send(message);
    }

    @SuppressWarnings("unchecked")
    @Override
    default void send(final Message message) {
        if (isClosed()) return;

        getQueue().add((T) message);

        if (isSuspended()) {
            return;
        }

        try {
            boolean deliver = true;

            while (deliver) {
                if (delivering.compareAndSet(false, true)) {
                    while (deliverAll())
                        ;
                    delivering.set(false);
                }
                deliver = false;
            }
        } catch (Throwable t) {
            if (delivering.get()) {
                delivering.set(false);
            }
            throw new RuntimeException(t.getMessage(), t);
        }
    }

    @Override
    public default void suspendExceptFor(final String name, final Class<?>... overrides) {
        suspendedOverrides.get().push(Arrays.asList(overrides));
    }

    @Override
    public default boolean isSuspended() {
        return !suspendedOverrides.get().empty();
    }

    @Override
    public default Message receive() {
        throw new UnsupportedOperationException("BlockingMailbox does not support this operation.");
    }

    @Override
    public default int pendingMessages() {
        return getQueue().size();
    }

    default boolean deliverAll() {
        boolean any = false;

        while (!getQueue().isEmpty()) {
            final Message queued = getQueue().poll();
            if (queued != null) {
                final Actor actor = queued.actor();
                if (actor != null) {
                    any = true;
                    actor.viewTestStateInitialization(null);
                    queued.deliver();
                }
            }
        }

        return any;
    }
}
