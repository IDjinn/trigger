import lombok.Getter;

@Getter
public enum ActionType {
    LOG(300),
    ;

    private final int type;

    ActionType(final int type) {
        this.type = type;
    }

}
