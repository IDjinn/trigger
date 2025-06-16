package idjinn.tools;

import idjinn.tools.actions.Action;
import idjinn.tools.conditions.Condition;
import idjinn.tools.conditions.Node;
import idjinn.tools.conditions.Operator;
import idjinn.tools.conditions.Value;
import idjinn.tools.events.Event;
import idjinn.tools.triggers.Trigger;
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

    private static final Logger log = LoggerFactory.getLogger(TriggerFactory.class);
    private static final Map<Integer, Class<? extends Action>> actionTypes = new ConcurrentHashMap<>();

    public static void registerPackage(final String packageName) {
        final var reflections = new Reflections(packageName, Scanners.SubTypes);
        final var subTypes = reflections.getSubTypesOf(Action.class);
        for (final var clazz : subTypes) {
            try {
                final var instance = (Action) clazz.getConstructor(String.class).newInstance("");
                TriggerFactory.actionTypes.put(instance.type(), clazz);
            } catch (final Exception e) {
                log.error("could not instantiate {}: {}", clazz.getSimpleName(), e.getMessage(), e);
            }
        }
    }

    public static Trigger createTrigger(final Element element) {
        final var id = Integer.parseInt(element.attributeValue("id"));
        final var type = Integer.parseInt(element.attributeValue("type"));
        final var name = element.attributeValue("name");
        return new Trigger(id, type, name);
    }

    public static Event createEvent(final Element element) {
        final var type = Integer.parseInt(element.attributeValue("type"));
        final var name = element.attributeValue("name");
        return new Event(type, name);
    }

    public static Node createConditionObject(final Element element) {
        return switch (element.getName()) {
            case "value" -> new Value(element.getData());
            case "op" -> new Operator(element.getData());
            default -> throw new IllegalArgumentException("unknown tag: " + element.getName());
        };
    }

    @SuppressWarnings("unchecked")
    public static Condition createCondition(final Element element) {
        final var type = Integer.parseInt(element.attributeValue("type"));
        final var name = element.attributeValue("name");

        final var tags = ((List<Element>) element.elements())
                .stream()
                .map(TriggerFactory::createConditionObject)
                .collect(Collectors.toCollection(ArrayList::new));

        return new Condition(type, name, tags);
    }

    public static Action createAction(final Element element) {
        final int type = Integer.parseInt(element.attributeValue("type"));
        final var name = element.attributeValue("name");
        final var actionClass = TriggerFactory.actionTypes.get(type);
        if (actionClass == null) {
            log.error("action {} was not found in type registry", type);
            throw new IllegalArgumentException("action type " + type + " was not found in type registry!");
        }

        try {
            return actionClass.getConstructor(String.class).newInstance(name);
        } catch (Exception e) {
            log.error("failed to instantiate action for type {}", type, e);
            throw new IllegalArgumentException("failed to instantiate action for type " + type, e);
        }
    }
}
