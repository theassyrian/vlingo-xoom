package io.vlingo.xoom.processor;

public abstract class Event implements Transition {

    private String sourceName = "";
    private String targetName = "";

    public Event() {
    }

    public Event(String source, String target) {
        this.sourceName = source;
        this.targetName = target;
    }

    @Override
    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public String getTargetName() {
        return targetName;
    }
    
    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getEventType() {
        return getSourceName() + "::" + getTargetName();
    }
}
