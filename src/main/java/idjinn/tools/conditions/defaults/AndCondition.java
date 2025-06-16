package idjinn.tools.conditions.defaults;


import idjinn.tools.conditions.Condition;
import idjinn.tools.conditions.DefaultConditionsTypes;
import idjinn.tools.conditions.Node;

import java.util.ArrayList;

public class AndCondition extends Condition {
    public AndCondition(final String name, final ArrayList<Node> nodes) {
        super(name, nodes);
    }

    @Override
    public int type() {
        return DefaultConditionsTypes.AND.getType();
    }
}
