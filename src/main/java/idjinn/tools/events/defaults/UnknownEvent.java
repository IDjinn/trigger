package idjinn.tools.events.defaults;

import idjinn.tools.events.Event;

public class UnknownEvent extends Event {
    public UnknownEvent(final int type, final String name) {
        super(type, name);
    }

    @Override
    public int getType() {
        return 0;
    }
}
