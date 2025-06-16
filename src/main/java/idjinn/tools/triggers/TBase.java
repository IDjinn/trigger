package idjinn.tools.triggers;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import idjinn.tools.actions.Action;
import idjinn.tools.conditions.Condition;
import idjinn.tools.events.Event;
import lombok.Data;

@Data
public abstract class TBase {
    private final int id;
    private final int type;
    private final String name;

    private final Multimap<Integer, Condition> conditions;
    private final Multimap<Integer, Action> actions;
    private final Multimap<Integer, Event> events;

    public TBase(final int id, final int type, final String name) {
        this.id = id;
        this.type = type;
        this.name = name;

        this.conditions = HashMultimap.create();
        this.actions = HashMultimap.create();
        this.events = HashMultimap.create();
    }

    public int id() {
        return this.id;
    }

    public int type() {
        return this.type;
    }

    public String name() {
        return this.name;
    }

    public void addEvent(final Event event) {
        this.events.put(event.getType(), event);
    }

    public void addCondition(final Condition condition) {
        this.conditions.put(condition.getType(), condition);
    }

    public void addAction(final Action action) {
        this.actions.put(action.type(), action);
    }
}
