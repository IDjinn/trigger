package idjinn.tools.trigger.actions;

import idjinn.tools.trigger.conditions.Node;
import idjinn.tools.trigger.events.Event;
import idjinn.tools.trigger.triggers.Trigger;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Optional;

@Data
public abstract class Action {
    private final String name;
    private final ArrayList<Node> nodes;

    @ToString.Exclude
    private Trigger trigger;

    public Action(final String name, final ArrayList<Node> nodes) {
        this.name = name;
        this.nodes = nodes;
    }

    public abstract void process(Event event);

    public abstract int type();

    public Optional<Object> firstNode() {
        return nodes.isEmpty() ? Optional.empty() : Optional.of(nodes.getFirst());
    }
}