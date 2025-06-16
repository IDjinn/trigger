package idjinn.tools.actions.buildings;

import idjinn.tools.actions.Action;
import idjinn.tools.events.Event;

public class UpgradeBuilding extends Action {
    public UpgradeBuilding(final String name) {
        super( name);
    }

    @Override
    public void process(final Event event) {

    }

    @Override
    public int type() {
        return 1;
    }
}
