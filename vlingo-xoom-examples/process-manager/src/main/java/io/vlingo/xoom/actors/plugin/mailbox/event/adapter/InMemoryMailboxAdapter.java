package io.vlingo.xoom.actors.plugin.mailbox.event.adapter;

import io.vlingo.actors.Message;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The {@link InMemoryMailboxAdapter} is a storage adapter implementation for the {@link EventMailboxStateAdapter}.
 * This adapter provides fast in-memory storage for {@link Message} mailbox queue for actors.
 *
 * @param <T> is a generic type that extends {@link Message}.
 * @author Kenny Bastani
 */
public class InMemoryMailboxAdapter<T extends Message> extends EventMailboxStateAdapter<T> {

    private final String MAILBOX_NAME = "inMemoryMailbox";
    private final ConcurrentLinkedQueue<T> queue = new ConcurrentLinkedQueue<>();
    private final int concurrency;

    public InMemoryMailboxAdapter(int concurrency) {
        System.out.println("Initializing inMemoryQueue...");
        this.concurrency = concurrency;
    }

    @Override
    public int concurrencyCapacity() {
        return concurrency;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <X extends Queue<T>> X getQueue() {
        return (X) queue;
    }

    @Override
    public String getName() {
        return MAILBOX_NAME;
    }

    @Override
    public void send(Message message) {
        System.out.println("Sending message from " + getName() + ": " + message.toString());
        super.send(message);
    }

    @Override
    public void publish(T message) {
        System.out.println("Publishing message from " + getName() + ": " + message.toString());
        super.publish(message);
    }

    @Override
    public Message receive() {
        System.out.println("Receive message from " + getName());
        return super.receive();
    }
}
