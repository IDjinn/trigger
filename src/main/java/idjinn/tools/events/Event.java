package idjinn.tools.events;

import idjinn.tools.TriggerSystem;
import idjinn.tools.triggers.TBase;
import idjinn.tools.triggers.Trigger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Event {
    private transient Object object;
    private transient Map<String, Object> context;
    private transient TriggerSystem triggerSystem;

    private final Map<String, Object> attributes;

    private Trigger trigger;
    private final int type;
    private final String name;

    public Event(final int type, final String name) {
        this.type = type;
        this.name = name;

        this.context = new ConcurrentHashMap<>();
        this.attributes = new ConcurrentHashMap<>();
    }

    public void setTrigger(final Trigger trigger) {
        this.trigger = trigger;
    }

    public TriggerSystem getTriggerSystem() {
        return triggerSystem;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public Object getObject() {
        return object;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
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
