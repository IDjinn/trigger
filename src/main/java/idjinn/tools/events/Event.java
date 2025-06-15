package idjinn.tools.events;

import idjinn.tools.triggers.TBase;

public class Event extends TBase {
    private TBase trigger;

    public Event(final int id, final int type, final String name) {
        super(id, type, name);
    }

    public void setTrigger(final TBase trigger) {
        this.trigger = trigger;
    }
}
