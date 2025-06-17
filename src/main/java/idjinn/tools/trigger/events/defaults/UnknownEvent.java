package idjinn.tools.trigger.events.defaults;

import idjinn.tools.trigger.events.Event;

public class UnknownEvent extends Event {
    public UnknownEvent(final String name) {
        super(name);
    }

    @Override
    public int type() {
        return 0;
    }
}
