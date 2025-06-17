package idjinn.tools.trigger.actions.defaults;

import idjinn.tools.trigger.actions.Action;
import idjinn.tools.trigger.actions.DefaultActionsTypes;
import idjinn.tools.trigger.conditions.Node;
import idjinn.tools.trigger.events.Event;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public class ActionLog extends Action {
    public ActionLog(final String name, final ArrayList<Node> nodes) {
        super(name, nodes);
    }

    @Override
    public void process(final Event event) {
        log.info(event.getContext().resolve(this.getNodes().getFirst().getData().toString()));
    }

    @Override
    public int type() {
        return DefaultActionsTypes.LOG.getType();
    }
}
