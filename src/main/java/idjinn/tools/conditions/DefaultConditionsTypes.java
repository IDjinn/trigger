package idjinn.tools.conditions;

import lombok.Getter;

@Getter
public enum DefaultConditionsTypes {
    UNKNOWN(0),

    AND(1),
    OR(2),
    NOT_EQUALS(3),
    EQUALS(4),

    GREATER_THAN(5),
    LESS_THAN(6),

    GREATER_THAN_OR_EQUAL(7),
    LESS_THAN_OR_EQUAL(8),


    ;

    private final int type;

    DefaultConditionsTypes(final int type) {
        this.type = type;
    }

}
