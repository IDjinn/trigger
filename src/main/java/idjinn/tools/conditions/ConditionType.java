package idjinn.tools.conditions;

public enum ConditionType {
    UNKNOWN(0),

    COMPARE_RESOURCES(1),
    COMPARE_GOLD(2),
    COMPARE_LORD_LEVEL(3),
    ;

    public int getType() {
        return type;
    }

    public final int type;
    ConditionType(final int type) {
        this.type = type;
    }
}
