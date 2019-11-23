package io.vlingo.xoom.actors.processor;

public abstract class Event<T extends State> implements Transition {

    private final T source;
    private final T target;

    public Event(T source, T target) {
        this.source = source;
        this.target = target;
    }

    @Override
    public String getSourceName() {
        return source.getName();
    }

    @Override
    public String getTargetName() {
        return target.getName();
    }

    public String getEventType() {
        return getSourceName() + "::" + getTargetName();
    }
}
