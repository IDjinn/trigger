package idjinn.tools.trigger;

import idjinn.tools.trigger.actions.Action;
import idjinn.tools.trigger.conditions.Condition;
import idjinn.tools.trigger.conditions.Node;
import idjinn.tools.trigger.conditions.Operator;
import idjinn.tools.trigger.conditions.Value;
import idjinn.tools.trigger.events.Event;
import idjinn.tools.trigger.triggers.Trigger;
import org.dom4j.Element;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class TriggerFactory {
    private final Logger log = LoggerFactory.getLogger(TriggerFactory.class);
    private final Map<Integer, Class<? extends Action>> actionTypes = new ConcurrentHashMap<>();
    private final Map<Integer, Class<? extends Condition>> conditionTypes = new ConcurrentHashMap<>();
    private final Map<Integer, Class<? extends Event>> eventTypes = new ConcurrentHashMap<>();

    public void registerPackage(final String packageName) {
        log.trace("registering package {} classes", packageName);
        final var targetType = (List<Node>) new ArrayList<Node>();

        final var reflections = new Reflections(packageName, Scanners.SubTypes);
        for (final var eventClass : reflections.getSubTypesOf(Event.class)) {
            try {
                final var instance = (Event) eventClass.getConstructor(String.class).newInstance("");
                this.eventTypes.put(instance.type(), eventClass);
            } catch (final Exception e) {
                log.error("could not instantiate {}: {}", eventClass.getSimpleName(), e.getMessage(), e);
            }
        }

        for (final var actionClass : reflections.getSubTypesOf(Action.class)) {
            try {
                final var instance = (Action) actionClass.getConstructor(String.class, targetType.getClass()).newInstance("", targetType);
                this.actionTypes.put(instance.type(), actionClass);
            } catch (final Exception e) {
                log.error("could not instantiate {}: {}", actionClass.getSimpleName(), e.getMessage(), e);
            }
        }

        for (final var conditionClass : reflections.getSubTypesOf(Condition.class)) {
            try {
                final var instance = (Condition) conditionClass.getConstructor(String.class, targetType.getClass()).newInstance("", targetType);
                this.conditionTypes.put(instance.type(), conditionClass);
            } catch (final Exception e) {
                log.error("could not instantiate {}: {}", conditionClass.getSimpleName(), e.getMessage(), e);
            }
        }
    }

    public Trigger createTrigger(final Element element) {
        final var id = Integer.parseInt(element.attributeValue("id"));
        final var type = Integer.parseInt(element.attributeValue("type"));
        final var name = element.attributeValue("name");
        return new Trigger(id, type, name);
    }

    @SuppressWarnings("unchecked")
    public Event createEvent(final Element element) {
        final var type = Integer.parseInt(element.attributeValue("type"));
        final var name = element.attributeValue("name");
        final var eventClass = this.eventTypes.get(type);
        if (eventClass == null) {
            log.error("event {} was not found in type registry", type);
            throw new IllegalArgumentException("event type " + type + " was not found in type registry!");
        }

        try {
            return eventClass.getConstructor(String.class).newInstance(name);
        } catch (Exception e) {
            log.error("failed to instantiate event for type {}", type, e);
            throw new IllegalArgumentException("failed to instantiate event for type " + type, e);
        }
    }

    public Node createNode(final Element element) {
        return switch (element.getName()) {
            case "value" -> new Value(element.getData());
            case "op" -> new Operator(element.getData());
            default -> throw new IllegalArgumentException("unknown tag: " + element.getName());
        };
    }


    @SuppressWarnings("unchecked")
    public Condition createCondition(final Element element) {
        final var type = Integer.parseInt(element.attributeValue("type"));
        final var name = element.attributeValue("name");

        final var tags = ((List<Element>) element.elements())
                .stream()
                .map(this::createNode)
                .collect(Collectors.toCollection(ArrayList::new));

        final var conditionClass = this.conditionTypes.get(type);
        if (conditionClass == null) {
            log.error("condition {} was not found in type registry", type);
            throw new IllegalArgumentException("condition type " + type + " was not found in type registry!");
        }

        try {
            return conditionClass.getConstructor(String.class, tags.getClass()).newInstance(name, tags);
        } catch (Exception e) {
            log.error("failed to instantiate condition for type {}", type, e);
            throw new IllegalArgumentException("failed to instantiate condition for type " + type, e);
        }
    }

    @SuppressWarnings("unchecked")
    public Action createAction(final Element element) {
        final int type = Integer.parseInt(element.attributeValue("type"));
        final var name = element.attributeValue("name");
        final var tags = ((List<Element>) element.elements())
                .stream()
                .map(this::createNode)
                .collect(Collectors.toCollection(ArrayList::new));

        final var actionClass = this.actionTypes.get(type);
        if (actionClass == null) {
            log.error("action {} was not found in type registry", type);
            throw new IllegalArgumentException("action type " + type + " was not found in type registry!");
        }

        try {
            return actionClass.getConstructor(String.class, tags.getClass()).newInstance(name, tags);
        } catch (Exception e) {
            log.error("failed to instantiate action for type {}", type, e);
            throw new IllegalArgumentException("failed to instantiate action for type " + type, e);
        }
    }
}
