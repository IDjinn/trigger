package idjinn.tools.triggers;

import idjinn.tools.events.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Trigger extends TBase {
    private static final Logger log = LoggerFactory.getLogger(Trigger.class);

    public Trigger(final int id, final int type, final String name) {
        super(id, type, name);
    }

    public void process(final Event event) {
        final var startTime = System.currentTimeMillis();
        for (final var e : this.getEvents().values()) {
            if (e.getType() != event.getType()) continue;

            for (final var condition : this.getConditions().values()) {
                final var conditionMatch = condition.process(event);
                log.trace("event: {}, condition: {}, match: {}", event, condition.type(), conditionMatch);
                if (!conditionMatch) return;
            }
        }

        for (final var action : this.getActions().values()) {
            log.trace("event: {}, action: {}", event, action);
            action.process(event);
        }

        log.trace("trigger: {} event: {} took {}ms", this.id(), event, System.currentTimeMillis() - startTime);
    }
}
