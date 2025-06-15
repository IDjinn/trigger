package idjinn.tools.conditions;

import idjinn.tools.triggers.TBase;

import java.util.Optional;

public class Condition extends TBase {

    private TBase trigger;

    public Condition(final int id, final int type, final String name) {
        super(id, type, name);
    }

    public void setTrigger(final TBase trigger) {
        this.trigger = trigger;
    }
}
