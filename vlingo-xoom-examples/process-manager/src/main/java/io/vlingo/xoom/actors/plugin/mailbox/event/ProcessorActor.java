package io.vlingo.xoom.actors.plugin.mailbox.event;

import io.vlingo.actors.Actor;
import io.vlingo.common.Completes;
import io.vlingo.common.Scheduled;
import io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.Kernel;
import io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.KernelActor;
import io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.State;

import java.util.List;

/**
 * The {@link ProcessorActor} is the default {@link Actor} implementation for a {@link Processor}.
 *
 * @author Kenny Bastani
 */
public abstract class ProcessorActor extends Actor implements Processor {

    private final List<State> states;
    private Kernel kernel;

    protected ProcessorActor(List<State> states) {
        this.states = states;
    }

    @Override
    public Completes<Boolean> shutDown() {
        stop();
        return completes().with(true);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Completes<Boolean> startUp() {
        logger().info("Starting " + this.definition().actorName() + "...");
        stage().scheduler().schedule(selfAs(Scheduled.class), null, 1000L, 100);
        this.kernel = stage().actorFor(Kernel.class, KernelActor.class);
        this.kernel.registerStates(states.toArray(new State[]{}));
        return completes().with(true);
    }

    @Override
    public Completes<Kernel> getKernel() {
        if (kernel != null) {
            return completes().with(kernel);
        } else {
            throw new IllegalStateException("The processor's kernel has not been initialized.");
        }
    }
}
