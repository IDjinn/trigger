package idjinn.tools.trigger.conditions;

public class Value extends Node {
    public Value(final Object data) {
        super(data);
    }

    @Override
    public String toString() {
        return "value = " + this.data.toString();
    }
}
