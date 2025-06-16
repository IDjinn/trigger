package idjinn.tools;

import idjinn.tools.actions.Action;
import idjinn.tools.conditions.Condition;
import idjinn.tools.conditions.Node;
import idjinn.tools.conditions.Operator;
import idjinn.tools.conditions.Value;
import idjinn.tools.events.Event;
import idjinn.tools.triggers.Trigger;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TriggerSystem {
    private static final Logger log = LoggerFactory.getLogger(TriggerSystem.class);
    private final Map<Integer, Action> actions;
    private final Map<Integer, Condition> conditions;
    private final Map<Integer, Trigger> triggers;

    public TriggerSystem(Map<Integer, Action> actions, Map<Integer, Condition> conditions, Map<Integer, Trigger> triggers) {
        this.actions = actions;
        this.conditions = conditions;
        this.triggers = triggers;
    }

    public TriggerSystem() {
        this.actions = new ConcurrentHashMap<>();
        this.conditions = new ConcurrentHashMap<>();
        this.triggers = new ConcurrentHashMap<>();
    }

    public void onEvent(final Event event) {

    }

    public List<Element> parseXMLSource(final String source) {
        final var elements = new ArrayList<Element>();
        final var reader = new SAXReader();
        try {
            final var read = reader.read(new InputSource(new StringReader(source)));
            final var rootElement = read.getRootElement();
            //noinspection unchecked
            elements.addAll(rootElement.elements("trigger"));
        } catch (final Exception e) {
            log.error("error reading triggers file: {}", e.getMessage(), e);
        }
        return elements;
    }

    public List<Element> parseXMLFromFile(final File file) {
        final var elements = new ArrayList<Element>();
        final var reader = new SAXReader();
        try {
            final var read = reader.read(file);
            final var rootElement = read.getRootElement();
            //noinspection unchecked
            elements.addAll(rootElement.elements("trigger"));
        } catch (final Exception e) {
            log.error("error reading triggers file: {}", e.getMessage(), e);
        }
        return elements;
    }

    public void init(final List<Element> elements) {
        for (final var element : elements) {
            try {
                final var trigger = Factory.createTrigger(element);
                final Element events = element.element("events");
                for (final var e : events.elements()) {
                    final var event = Factory.createEvent((Element) e);
                    event.setTrigger(trigger);
                    trigger.addEvent(event);
                }

                final Element conditions = element.element("conditions");
                for (final var c : conditions.elements()) {
                    final var condition = Factory.createCondition((Element) c);
                    condition.setTrigger(trigger);
                    trigger.addCondition(condition);
                }

                final Element actions = element.element("actions");
                for (final var a : actions.elements()) {
                    final var action = Factory.createAction((Element) a);
                    action.setTrigger(trigger);
                    trigger.addAction(action);
                }

                this.registerTrigger(trigger);
            } catch (final Exception e) {
                log.error("trigger init error: {}", e.getMessage(), e);
            }
        }
    }

    private void registerTrigger(final Trigger trigger) {
        this.triggers.put(trigger.id(), trigger);
        log.debug("added trigger {} with name {}", trigger.id(), trigger.name());
    }

    public Map<Integer, Action> getActions() {
        return actions;
    }

    public Map<Integer, Condition> getConditions() {
        return conditions;
    }

    public Map<Integer, Trigger> getTriggers() {
        return triggers;
    }
}
