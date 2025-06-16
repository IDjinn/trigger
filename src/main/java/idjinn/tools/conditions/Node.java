package idjinn.tools.conditions;

public abstract class Node {
    protected final Object data;

    public Node(final Object data) {
        this.data = data;
    }

    public final Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return this.data.toString();
    }
}
