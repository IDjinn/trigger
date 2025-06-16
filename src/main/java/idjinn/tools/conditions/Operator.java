package idjinn.tools.conditions;

public class Operator extends Node {
    public Operator(final Object data) {
        super(data);
    }

    @Override
    public String toString() {
        return "operator = " + this.data.toString();
    }
}
