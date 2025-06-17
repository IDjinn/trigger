package idjinn.tools.trigger.triggers;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import idjinn.tools.trigger.TriggerContext;
import idjinn.tools.trigger.actions.Action;
import idjinn.tools.trigger.conditions.Condition;
import idjinn.tools.trigger.events.Event;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Data
public class Trigger {
    private static final Logger log = LoggerFactory.getLogger(Trigger.class);

    private final int id;
    private final int type;
    private final String name;

    private final Multimap<Integer, Condition> conditions;
    private final Multimap<Integer, Action> actions;
    private final Multimap<Integer, Event> events;

    private final Map<String, Object> variables;
    public Trigger(final int id, final int type, final String name) {
        this.id = id;
        this.type = type;
        this.name = name;

        this.conditions = HashMultimap.create();
        this.actions = HashMultimap.create();
        this.events = HashMultimap.create();
        this.variables = new HashMap<>();
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
        this.events.put(event.type(), event);
    }

    public void addCondition(final Condition condition) {
        this.conditions.put(condition.type(), condition);
    }

    public void addAction(final Action action) {
        this.actions.put(action.type(), action);
    }

    public void process(final TriggerContext context, final Event event) {
        final var startTime = System.currentTimeMillis();
        for (final var e : this.events.values()) {
            if (e.type() != event.type()) continue;

            for (final var condition : this.conditions.values()) {
                final var conditionMatch = condition.matches(context, event);
                log.trace("event: {}, condition: {}, match: {}", event, condition.type(), conditionMatch);
                if (!conditionMatch) return;
            }

            for (final var action : this.actions.values()) {
                log.trace("event: {}, action: {}", event, action);
                action.process(event);
            }

            log.trace("trigger: {} event: {} took {}ms", this.id(), event, System.currentTimeMillis() - startTime);
        }
    }
}
