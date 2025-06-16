package idjinn.tools.conditions;

import lombok.Getter;

@Getter
public enum ConditionType {
    UNKNOWN(0),

    COMPARE_RESOURCES(1),
    COMPARE_GOLD(2),
    COMPARE_LORD_LEVEL(3),
    ;

    public final int type;
    ConditionType(final int type) {
        this.type = type;
    }
}
