package idjinn.tools.conditions;

import lombok.Getter;

@Getter
public enum DefaultConditionsTypes {
    UNKNOWN(0),

    COMPARISON(1),


    ;

    private final int type;

    DefaultConditionsTypes(final int type) {
        this.type = type;
    }

}
