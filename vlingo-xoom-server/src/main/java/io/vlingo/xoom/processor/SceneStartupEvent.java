package io.vlingo.xoom.processor;

import io.micronaut.context.event.ApplicationEvent;
import io.vlingo.xoom.VlingoScene;

public class SceneStartupEvent extends ApplicationEvent {

    private final VlingoScene source;

    public SceneStartupEvent(VlingoScene source) {
        super(source);
        this.source = source;
    }

    @Override
    public VlingoScene getSource() {
        return source;
    }
}
