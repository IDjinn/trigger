package idjinn.tools.conditions.defaults;


import idjinn.tools.conditions.DefaultConditionsTypes;
import idjinn.tools.conditions.Node;

public class LessThanCondition extends ComparisonCondition {

    public LessThanCondition(final String name, final Node left, final Node right) {
        super(name, left, right);
    }

    @Override
    public int type() {
        return DefaultConditionsTypes.LESS_THAN.getType();
    }
}
