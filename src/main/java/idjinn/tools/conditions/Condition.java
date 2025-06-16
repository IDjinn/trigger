package idjinn.tools.conditions;

import idjinn.tools.events.Event;
import idjinn.tools.triggers.TBase;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

@Data
@ToString(exclude = "trigger")
public class Condition {
    private final int type;
    private final String name;
    private final ArrayList<Node> nodes;
    private transient TBase trigger;

    public Condition(final int type, final String name, final ArrayList<Node> nodes) {
        this.type = type;
        this.name = name;
        this.nodes = nodes;
    }

    public boolean process(final Event event) {
        return true;
    }
}
