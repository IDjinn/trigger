package idjinn.tools.events;

import lombok.Data;
import lombok.ToString;

@Data
public abstract class Event {
    @ToString.Exclude
    private final transient Object locker;

    private final String name;

    public Event(final String name) {
        this.name = name;

        this.locker = new Object();
    }

    public abstract int type();
}
