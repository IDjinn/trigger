package idjinn.tools.trigger.conditions;

import idjinn.tools.trigger.events.Event;
import idjinn.tools.trigger.triggers.Trigger;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
public abstract class Condition {
    private final String name;
    private final List<Node> nodes;

    @ToString.Exclude
    private transient Trigger trigger;

    public Condition(final String name, final List<Node> nodes) {
        this.name = name;
        this.nodes = nodes;
    }

    public abstract boolean matches(final Event event);

    public abstract int type();
}
