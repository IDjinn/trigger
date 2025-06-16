package idjinn.tools.conditions;

import idjinn.tools.triggers.TBase;

import java.util.ArrayList;

public class Condition {
    private final int type;
    private final String name;
    private final ArrayList<Node> nodes;
    private TBase trigger;

    public Condition(final int type, final String name, final ArrayList<Node> nodes) {
        this.type = type;
        this.name = name;
        this.nodes = nodes;
    }

    public void setTrigger(final TBase trigger) {
        this.trigger = trigger;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }
}
