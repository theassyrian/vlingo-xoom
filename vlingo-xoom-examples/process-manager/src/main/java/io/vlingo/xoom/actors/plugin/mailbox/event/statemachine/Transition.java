package io.vlingo.xoom.actors.plugin.mailbox.event.statemachine;

public interface Transition {

    String getSourceName();
    String getTargetName();
}
