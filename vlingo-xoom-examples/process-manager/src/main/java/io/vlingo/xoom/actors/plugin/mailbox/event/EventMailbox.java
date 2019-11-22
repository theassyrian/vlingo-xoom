package io.vlingo.xoom.actors.plugin.mailbox.event;

import io.vlingo.actors.Message;

import java.util.Queue;

public interface EventMailbox<T extends Message> extends BasicMailbox<T>{

    @Override
    <X extends Queue<T>> X getQueue();
}
