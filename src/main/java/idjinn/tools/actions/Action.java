package idjinn.tools.actions;

import idjinn.tools.events.Event;
import idjinn.tools.triggers.Trigger;
import lombok.Data;
import lombok.ToString;

@Data
public abstract class Action {
    private final String name;

    @ToString.Exclude
    private Trigger trigger;

    public Action( final String name) {
        this.name = name;
    }

    public abstract void process(Event event);

    public abstract int type();
}