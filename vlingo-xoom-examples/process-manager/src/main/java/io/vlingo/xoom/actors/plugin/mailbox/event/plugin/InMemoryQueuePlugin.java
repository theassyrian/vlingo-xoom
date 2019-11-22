package io.vlingo.xoom.actors.plugin.mailbox.event.plugin;

import io.vlingo.actors.Configuration;
import io.vlingo.actors.Dispatcher;
import io.vlingo.actors.Mailbox;
import io.vlingo.actors.Registrar;
import io.vlingo.actors.plugin.AbstractPlugin;
import io.vlingo.actors.plugin.Plugin;
import io.vlingo.actors.plugin.PluginConfiguration;
import io.vlingo.actors.plugin.PluginProperties;
import io.vlingo.xoom.actors.plugin.mailbox.event.adapter.EventMailboxStateAdapter;
import io.vlingo.xoom.actors.plugin.mailbox.event.adapter.InMemoryMailboxAdapter;

/**
 * The {@link InMemoryQueuePlugin} is a mailbox queue that configures an in-memory queue of messages for the
 * {@link EventMailboxStateAdapter}.
 *
 * @author Kenny Bastani
 */
public class InMemoryQueuePlugin extends AbstractPlugin implements EventMailboxStatePlugin {

    private final InMemoryQueuePlugin.InMemoryQueuePluginConfiguration configuration;

    public InMemoryQueuePlugin() {
        this.configuration = new InMemoryQueuePlugin.InMemoryQueuePluginConfiguration();
    }

    @Override
    public void close() {
    }

    @Override
    public PluginConfiguration configuration() {
        return configuration;
    }

    @Override
    public String name() {
        return configuration.name();
    }

    @Override
    public int pass() {
        return 1;
    }

    @Override
    public void start(final Registrar registrar) {
        registrar.register(name(), false, this);
    }

    @Override
    public Plugin with(final PluginConfiguration overrideConfiguration) {
        return this;
    }

    @Override
    public Mailbox provideMailboxFor(final int hashCode) {
        return new InMemoryMailboxAdapter(10);
    }

    @Override
    public Mailbox provideMailboxFor(final int hashCode, final Dispatcher dispatcher) {
        return new InMemoryMailboxAdapter(10);
    }

    public static class InMemoryQueuePluginConfiguration implements PluginConfiguration {

        private static final String NAME = "inMemoryQueue";

        public InMemoryQueuePluginConfiguration() {
        }

        @Override
        public void build(final Configuration configuration) {
        }

        @Override
        public void buildWith(final Configuration configuration, final PluginProperties properties) {
        }

        @Override
        public String name() {
            return NAME;
        }
    }
}
