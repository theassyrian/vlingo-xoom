package io.vlingo.xoom.actors.plugin.mailbox.event.plugin;

import io.vlingo.actors.MailboxProvider;
import io.vlingo.actors.plugin.Plugin;
import io.vlingo.xoom.actors.plugin.mailbox.event.EventMailbox;

/**
 * The {@link EventMailboxStatePlugin} describes a plugin for adapting persistent backing stores for an
 * {@link EventMailbox} implementation. This plugin abstracts an underlying storage mechanism for publishing
 * and consuming streams of events from a source.
 *
 * @author Kenny Bastani
 */
public interface EventMailboxStatePlugin extends Plugin, MailboxProvider {

}
