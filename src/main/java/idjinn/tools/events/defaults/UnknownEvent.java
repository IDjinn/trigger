package idjinn.tools.events.defaults;

import idjinn.tools.events.Event;

public class UnknownEvent extends Event {
    public UnknownEvent(final String name) {
        super(name);
    }

    @Override
    public int type() {
        return 0;
    }
}
