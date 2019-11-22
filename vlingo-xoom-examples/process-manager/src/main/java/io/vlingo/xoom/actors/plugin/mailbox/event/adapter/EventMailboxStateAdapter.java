package io.vlingo.xoom.actors.plugin.mailbox.event.adapter;

import io.vlingo.actors.Message;
import io.vlingo.xoom.actors.plugin.mailbox.event.BasicMailbox;

public abstract class EventMailboxStateAdapter<T extends Message> implements BasicMailbox<T> {

    public abstract String getName();
}
