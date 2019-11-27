package io.examples.domain;

import io.examples.data.Identity;
import io.examples.domain.state.OrganizationConfirmed;
import io.examples.domain.state.OrganizationPending;
import io.vlingo.xoom.processor.Processor;
import io.vlingo.xoom.processor.State;
import io.vlingo.xoom.processor.StateTransition;

import javax.persistence.Entity;
import java.util.Optional;
import java.util.function.Consumer;

@Entity
public class Organization extends Identity {

    private OrganizationStatus status = OrganizationStatus.CREATED;

    public Organization() {
    }

    public OrganizationStatus getStatus() {
        return status;
    }

    public Organization create(Processor processor) {
        return sendEvent(processor, new OrganizationPending());
    }

    public Organization confirm(Processor processor) {
        return sendEvent(processor, new OrganizationConfirmed());
    }

    private Organization sendEvent(Processor processor, State targetState) {
        OrganizationEvent event = new OrganizationEvent(status.name(), targetState.getName());
        return sendEvent(processor, event, defaultStateConsumer(targetState));
    }

    private Organization sendEvent(Processor processor, OrganizationEvent event, Consumer<StateTransition> handler) {
        return Optional.ofNullable(processor.applyEvent(event)
                .andThenConsume(handler).otherwise(transition -> null).await())
                .map(transition -> this).orElseThrow(() -> new RuntimeException("The event with type ["
                        + event.getEventType() +
                        "] does not match a valid transition handler in the processor kernel."));
    }

    @SuppressWarnings("unchecked")
    private Consumer<StateTransition> defaultStateConsumer(State targetState) {
        return stateTransition -> {
            stateTransition.apply(targetState).await();
            this.status = OrganizationStatus.valueOf(stateTransition.getTargetName());
            this.version = targetState.getVersion().toString();
        };
    }
}