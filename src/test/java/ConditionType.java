import lombok.Getter;

@Getter
public enum ConditionType {
    COMPARE_RESOURCES(300),
    COMPARE_GOLD(301),
    COMPARE_LORD_LEVEL(302),
    ;

    public final int type;
    ConditionType(final int type) {
        this.type = type;
    }
}
