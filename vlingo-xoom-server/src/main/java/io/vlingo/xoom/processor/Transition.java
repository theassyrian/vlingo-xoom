package io.vlingo.xoom.processor;

/**
 * A {@link Transition} is a base interface for a {@link StateTransition} and describes the identity of a source
 * state and a target state.
 *
 * @author Kenny Bastani
 */
public interface Transition {

    String getSourceName();

    String getTargetName();
}
