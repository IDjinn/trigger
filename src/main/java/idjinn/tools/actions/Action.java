package idjinn.tools.actions;

import idjinn.tools.events.Event;
import idjinn.tools.triggers.TBase;
import idjinn.tools.triggers.Trigger;

import java.util.Optional;

public abstract class Action {
    private final String name;
    private Trigger trigger;

    public Action( final String name) {
        this.name = name;
    }

    public abstract void process(Event event);

    public abstract int type();

    public void setTrigger(final Trigger trigger) {
        this.trigger = trigger;
    }

    public Trigger getTrigger() {
        return trigger;
    }
}