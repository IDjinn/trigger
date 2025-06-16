package idjinn.tools.triggers;

import idjinn.tools.actions.Action;
import idjinn.tools.conditions.Condition;
import idjinn.tools.events.Event;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class TBase {
    private final int id;
    private final int type;
    private final String name;

    private final Map<Integer, Condition> conditions;
    private final Map<Integer, Action> actions;
    private final Map<Integer, Event> events;

    public TBase(final int id, final int type, final String name) {
        this.id = id;
        this.type = type;
        this.name = name;

        this.conditions = new ConcurrentHashMap<>();
        this.actions = new ConcurrentHashMap<>();
        this.events = new ConcurrentHashMap<>();
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
//        this.events.put(event.id(), event);
    }

    public void addCondition(final Condition condition) {
        this.conditions.put(condition.id(), condition);
    }

    public void addAction(final Action action) {
        this.actions.put(action.type(), action);
    }

    public Map<Integer, Condition> getConditions() {
        return conditions;
    }

    public Map<Integer, Action> getActions() {
        return actions;
    }

    public Map<Integer, Event> getEvents() {
        return events;
    }
}
