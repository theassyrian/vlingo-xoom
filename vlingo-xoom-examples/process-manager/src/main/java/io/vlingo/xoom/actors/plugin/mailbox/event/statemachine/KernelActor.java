package io.vlingo.xoom.actors.plugin.mailbox.event.statemachine;

import io.vlingo.actors.Actor;
import io.vlingo.common.Completes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KernelActor extends Actor implements Kernel {
    private List<State> stateList = new ArrayList<>();

    @Override
    public void registerStates(State... states) {
        stateList.addAll(Stream.of(states).collect(Collectors.toList()));
    }

    @Override
    public Completes<List<State>> getStates() {
        return completes().with(Collections.unmodifiableList(stateList));
    }

    @Override
    public Completes<List<StateTransition>> getStateTransitions() {
        return completes().with(Collections.unmodifiableList(stateList.stream()
                .map(state -> Arrays.stream(state.getTransitionHandlers()))
                .flatMap(handlers -> handlers)
                .map(KernelActor::apply)
                .map(Completes::outcome)
                .collect(Collectors.toList())));
    }

    @SuppressWarnings("unchecked")
    private static Completes<StateTransition> apply(TransitionHandler transitionHandler) {
        return (Completes<StateTransition>) transitionHandler.getStateTransition();
    }
}
