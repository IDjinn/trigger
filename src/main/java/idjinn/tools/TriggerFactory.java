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

final class TriggerFactory {
    private static final Logger log = LoggerFactory.getLogger(TriggerFactory.class);
    private static final Map<Integer, Class<? extends Action>> ACTION_TYPES = new ConcurrentHashMap<>();
    private static final Map<Integer, Class<? extends Condition>> CONDITION_TYPES = new ConcurrentHashMap<>();

    public static void registerPackage(final String packageName) {
        final var targetType = (List<Node>) new ArrayList<Node>();

        final var reflections = new Reflections(packageName, Scanners.SubTypes);
        for (final var actionClass : reflections.getSubTypesOf(Action.class)) {
            try {
                final var instance = (Action) actionClass.getConstructor(String.class, targetType.getClass()).newInstance("", targetType);
                TriggerFactory.ACTION_TYPES.put(instance.type(), actionClass);
            } catch (final Exception e) {
                log.error("could not instantiate {}: {}", actionClass.getSimpleName(), e.getMessage(), e);
            }
        }

        for (final var conditionClass : reflections.getSubTypesOf(Condition.class)) {
            try {
                final var instance = (Condition) conditionClass.getConstructor(String.class, targetType.getClass()).newInstance("", targetType);
                TriggerFactory.CONDITION_TYPES.put(instance.type(), conditionClass);
            } catch (final Exception e) {
                log.error("could not instantiate {}: {}", conditionClass.getSimpleName(), e.getMessage(), e);
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

    public static Node createNode(final Element element) {
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
                .map(TriggerFactory::createNode)
                .collect(Collectors.toCollection(ArrayList::new));

        final var conditionClass = TriggerFactory.CONDITION_TYPES.get(type);
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
    public static Action createAction(final Element element) {
        final int type = Integer.parseInt(element.attributeValue("type"));
        final var name = element.attributeValue("name");
        final var tags = ((List<Element>) element.elements())
                .stream()
                .map(TriggerFactory::createNode)
                .collect(Collectors.toCollection(ArrayList::new));

        final var actionClass = TriggerFactory.ACTION_TYPES.get(type);
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
