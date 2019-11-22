package io.vlingo.xoom.actors.plugin.mailbox.event;

import io.vlingo.actors.*;
import io.vlingo.common.Completes;
import io.vlingo.xoom.actors.Properties;
import io.vlingo.xoom.actors.plugin.mailbox.event.adapter.EventMailboxStateAdapter;

public class EventProducer<T extends Message, R extends EventMailboxStateAdapter<T>> {
    private final R eventMailbox;

    public EventProducer(R eventMailbox) {
        this.eventMailbox = eventMailbox;
    }


}

