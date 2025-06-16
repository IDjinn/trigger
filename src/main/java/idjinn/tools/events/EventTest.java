package idjinn.tools.events;

public class EventTest extends Event {
    public EventTest(final int type, final String name) {
        super(type, name);
    }

    @Override
    public int getType() {
        return 1;
    }
}
