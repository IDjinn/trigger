package idjinn.tools.events;

import lombok.Getter;

@Getter
public enum EventType {
    UNKNOWN(0),

    ON_LOGIN(1),
    ON_LOGOUT(2),

    ON_REGISTER(3),

    CASTLE_UPGRADED(4),
    BUILDING_UPGRADED(5),
    BUILDING_CREATED(6),

    LORD_LEVEL_UPGRADED(7),
    ;

    private final int type;

    EventType(final int type) {
        this.type = type;
    }

}
