package idjinn.tools.conditions;

import idjinn.tools.events.Event;
import idjinn.tools.triggers.TBase;
import lombok.ToString;

import java.util.List;

public abstract class Condition {
    private final String name;
    private final List<Node> nodes;

    @ToString.Exclude
    private transient TBase trigger;

    public Condition(final String name, final List<Node> nodes) {
        this.name = name;
        this.nodes = nodes;
    }

    public boolean process(final Event event) {
        return true;
    }

    public abstract int type();
}
