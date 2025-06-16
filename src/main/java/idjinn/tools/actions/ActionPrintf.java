package idjinn.tools.actions;

import idjinn.tools.conditions.Node;
import idjinn.tools.events.Event;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public class ActionPrintf extends Action {
    public ActionPrintf(final String name, final ArrayList<Node> nodes) {
        super(name, nodes);
    }

    @Override
    public void process(final Event event) {
        log.info(this.getNodes().getFirst().getData().toString());
    }

    @Override
    public int type() {
        return 1;
    }
}
