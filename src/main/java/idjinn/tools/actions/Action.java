package idjinn.tools.actions;

import idjinn.tools.triggers.TBase;

import java.util.Optional;

public class Action extends TBase {
    private TBase trigger;

    public Action(final int id, final int type, final String name) {
        super(id, type, name);
    }

    public void setTrigger(final TBase trigger) {
        this.trigger = trigger;
    }
}