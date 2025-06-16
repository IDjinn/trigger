package idjinn.tools.conditions;

public abstract class Node {
    protected final Object data;

    public Node(final Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return this.data.toString();
    }
}
