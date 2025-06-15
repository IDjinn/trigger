package idjinn.tools.actions;

public enum ActionType {
    UNKNOWN(0),

    UPGRADE_BUILDING(1),

    ;

    private final int type;

    ActionType(final int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
