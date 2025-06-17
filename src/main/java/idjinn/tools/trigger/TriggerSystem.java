package idjinn.tools.trigger;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import idjinn.tools.trigger.actions.Action;
import idjinn.tools.trigger.conditions.Condition;
import idjinn.tools.trigger.events.Event;
import idjinn.tools.trigger.triggers.Trigger;
import lombok.Data;
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

@Data
public class TriggerSystem {
    private static final Logger log = LoggerFactory.getLogger(TriggerSystem.class);
    private final Multimap<Integer, Action> actions;
    private final Multimap<Integer, Condition> conditions;
    private final Map<Integer, Trigger> triggers;
    private final TriggerContext context = new TriggerContext(this);

    private final TriggerFactory triggerFactory = new TriggerFactory();
    private final String packageName;

    public TriggerSystem() {
        this.packageName = "";
        this.actions = HashMultimap.create();
        this.conditions = HashMultimap.create();
        this.triggers = new ConcurrentHashMap<>();
    }

    public TriggerSystem(final String packageName) {
        this.packageName = packageName;
        this.actions = HashMultimap.create();
        this.conditions = HashMultimap.create();
        this.triggers = new ConcurrentHashMap<>();

        this.triggerFactory.registerPackage(this.packageName);
    }

    public void onEvent(final TriggerContext context, final Event event) {
        synchronized (event.getLocker()) {
//            event.setTriggerSystem(this);
            event.setContext(context);
            for (final var trigger : this.triggers.values()) {
                trigger.process(context, event);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public List<Element> parseXMLSource(final String source) {
        final var elements = new ArrayList<Element>();
        final var reader = new SAXReader();
        try {
            final var read = reader.read(new InputSource(new StringReader(source)));
            final var rootElement = read.getRootElement();
            elements.addAll(rootElement.elements("trigger"));
        } catch (final Exception e) {
            log.error("error reading triggers file: {}", e.getMessage(), e);
        }
        return elements;
    }

    @SuppressWarnings("unchecked")
    public List<Element> parseXMLFromFile(final File file) {
        final var elements = new ArrayList<Element>();
        final var reader = new SAXReader();
        try {
            final var read = reader.read(file);
            final var rootElement = read.getRootElement();
            elements.addAll(rootElement.elements("trigger"));
        } catch (final Exception e) {
            log.error("error reading triggers file: {}", e.getMessage(), e);
        }
        return elements;
    }

    public void registerDefaults() {
        this.triggerFactory.registerPackage("idjinn.tools");
    }

    public void init(final List<Element> elements) {
        for (final var element : elements) {
            try {
                final var trigger = this.triggerFactory.createTrigger(element);
                final Element events = element.element("events");
                for (final var e : events.elements()) {
                    final var event = this.triggerFactory.createEvent((Element) e);
//                    event.setTrigger(trigger);
                    trigger.addEvent(event);
                }

                final Element conditions = element.element("conditions");
                for (final var c : conditions.elements()) {
                    final var condition = this.triggerFactory.createCondition((Element) c);
                    condition.setTrigger(trigger);
                    trigger.addCondition(condition);
                }

                final Element actions = element.element("actions");
                for (final var a : actions.elements()) {
                    final var action = this.triggerFactory.createAction((Element) a);
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
}
