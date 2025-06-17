package idjinn.tools.trigger.actions;

import lombok.Getter;

@Getter
public enum DefaultActionsTypes {
    UNKNOWN(0),

    LOG(1);

    private final int type;

    DefaultActionsTypes(final int type) {
        this.type = type;
    }

}
