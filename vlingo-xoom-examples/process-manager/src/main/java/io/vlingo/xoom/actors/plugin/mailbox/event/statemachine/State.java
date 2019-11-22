package io.vlingo.xoom.actors.plugin.mailbox.event.statemachine;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A {@link State} is an interface definition that should describe a collection of input states and output states.
 *
 * @author Kenny Bastani
 */
public abstract class State<T> {

    private Long createdAt;
    private UUID version;

    public State() {
        this.createdAt = new Date().getTime();
        this.version = UUID.randomUUID();
    }

    public Long getCreatedAt() {
        return this.createdAt;
    }

    public UUID getVersion() {
        return version;
    }

    public abstract String getName();

    public abstract TransitionHandler[] getTransitionHandlers();

    public Map<String, Set<String>> toMap() {
        Map<String, Set<String>> result = new HashMap<>();

        Stream.of(getTransitionHandlers())
                .map(t -> (Transition) t.getStateTransition().outcome())
                .forEach(t -> result.compute(t.getSourceName(), (k, v) -> {
                    Set<String> values = Optional.ofNullable(v)
                            .orElseGet(HashSet::new);
                    values.add(t.getTargetName());
                    return values;
                }));

        return result;
    }

    public String toGraph() {
        return this.toMap().entrySet().stream()
                .map((kv) -> "(" + kv.getKey() + ")" + "-->(" +
                        Arrays.toString(kv.getValue().toArray()) + ")")
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String toString() {
        return "State{" +
                "createdAt=" + createdAt +
                ", version=" + version +
                '}';
    }
}
