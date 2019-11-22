package io.vlingo.xoom.actors.plugin.mailbox.event.adapter;

import io.vlingo.actors.Message;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

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
}
