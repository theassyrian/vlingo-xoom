package io.vlingo.xoom.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class TransitionBuilder<T extends State, R extends State> {

    private T source;
    private R target;
    private List<BiConsumer<T, R>> actions = new ArrayList<>();

    private TransitionBuilder(T source) {
        this.source = source;
    }

    private TransitionBuilder(T source, R target) {
        this.source = source;
        this.target = target;
    }

    public <R1 extends State> TransitionBuilder2<T, R1> to(R1 target) {
        return new TransitionBuilder2<>(source, target);
    }

    public static <T1 extends State> TransitionBuilder<T1, ?> from(T1 source) {
        return new TransitionBuilder<>(source);
    }

    public static class TransitionBuilder2<T2 extends State, R2 extends State> {
        private T2 source;
        private R2 target;

        private TransitionBuilder2(T2 source, R2 target) {
            this.source = source;
            this.target = target;
        }

        public StateTransition<T2, R2> then(BiConsumer<T2, R2> action) {
            StateTransition<T2, R2> transition = new StateTransition<>(source, target);
            transition.setActionHandler(action);
            return transition;
        }
    }

    public static class TransitionBuilder3<T2 extends State, R2 extends State> {
        private T2 source;
        private R2 target;
        private BiConsumer<T2, R2> action;

        private TransitionBuilder3(T2 source, R2 target, BiConsumer<T2, R2> action) {
            this.source = source;
            this.target = target;
            this.action = action;
        }

        public StateTransition<T2, R2> build() {
            StateTransition<T2, R2> transition = new StateTransition<>(source, target);
            transition.setActionHandler(action);
            return transition;
        }
    }
}
