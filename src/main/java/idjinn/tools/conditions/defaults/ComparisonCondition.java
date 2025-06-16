package idjinn.tools.conditions.defaults;

import idjinn.tools.conditions.Condition;
import idjinn.tools.conditions.Node;

import java.util.List;

public abstract class ComparisonCondition extends Condition {
    public ComparisonCondition(final String name, final Node left, final Node right) {
        super(name, List.of(left, right));
    }
}
