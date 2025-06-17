package idjinn.tools.trigger.conditions;

import idjinn.tools.trigger.events.Event;

import java.util.ArrayList;

public class ComparisonCondition extends Condition {
    public ComparisonCondition(final String name, final ArrayList<Node> nodes) {
        super(name, nodes);
    }

    public Node left() {
        return this.getNodes().getFirst();
    }

    public Node operator() {
        return this.getNodes().get(1);
    }

    public Node right() {
        return this.getNodes().getLast();
    }

    @Override
    public boolean matches(final Event event) {
        final var left = this.left().getData();
        final var right = this.right().getData();

        return switch (this.operator().getData().toString()) {
            case "==" -> left.equals(right);
            case "!=" -> !left.equals(right);
            case ">" -> toDouble(left) > toDouble(right);
            case "<" -> toDouble(left) < toDouble(right);
            case ">=" -> toDouble(left) >= toDouble(right);
            case "<=" -> toDouble(left) <= toDouble(right);
            default -> false;
        };
    }

    private double toDouble(Object obj) {
        if (obj instanceof Number) return ((Number) obj).doubleValue();
        throw new IllegalArgumentException("operator is not a valid number: " + obj);
    }

    @Override
    public int type() {
        return DefaultConditionsTypes.COMPARISON.getType();
    }
}
