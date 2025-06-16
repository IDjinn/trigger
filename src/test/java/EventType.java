import lombok.Getter;

@Getter
public enum EventType {
    ON_LOGIN(300),
    ON_LOGOUT(301),
    ON_REGISTER(302),
    ;

    private final int type;

    EventType(final int type) {
        this.type = type;
    }

}
