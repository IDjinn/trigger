package idjinn.tools.conditions;

import idjinn.tools.triggers.TBase;

import java.util.ArrayList;

public class Condition extends TBase {

    private final ArrayList<Node> tags;
    private TBase trigger;

    public Condition(final int id, final int type, final String name, final ArrayList<Node> tags) {
        super(id, type, name);
        this.tags = tags;
    }

    public void setTrigger(final TBase trigger) {
        this.trigger = trigger;
    }

    public ArrayList<Node> getTags() {
        return tags;
    }
}
