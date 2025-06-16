package idjinn.tools.conditions.defaults;


import idjinn.tools.conditions.DefaultConditionsTypes;
import idjinn.tools.conditions.Node;

public class OrCondition extends ComparisonCondition {

    public OrCondition(final String name, final Node left, final Node right) {
        super(name, left, right);
    }

    @Override
    public int type() {
        return DefaultConditionsTypes.OR.getType();
    }
}
