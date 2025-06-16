package idjinn.tools.events;

import idjinn.tools.TriggerSystem;
import idjinn.tools.triggers.Trigger;
import lombok.Data;
import lombok.ToString;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Event {
    private transient Object object;
    private transient Map<String, Object> context;
    private transient TriggerSystem triggerSystem;
    private final Map<String, Object> attributes;

    @ToString.Exclude
    private final transient Object locker;

    @ToString.Exclude
    private transient Trigger trigger;

    private final int type;
    private final String name;

    public Event(final int type, final String name) {
        this.type = type;
        this.name = name;

        this.context = new ConcurrentHashMap<>();
        this.attributes = new ConcurrentHashMap<>();
        this.locker = new Object();
    }

    public Event setContext(final Map<String, Object> context) {
        this.context.clear();
        this.context.putAll(context);
        return this;
    }

    public Event addAttribute(final String key, final Object value) {
        this.attributes.put(key, value);
        return this;
    }
}
