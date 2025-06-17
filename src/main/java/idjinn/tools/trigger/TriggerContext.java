package idjinn.tools.trigger;

import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Data
public class TriggerContext {
    @ToString.Exclude
    private final TriggerSystem triggerSystem;

    private final Map<String, Object> variables;

    public TriggerContext(final TriggerSystem triggerSystem) {
        this.triggerSystem = triggerSystem;
        this.variables = new HashMap<>();
    }

    public TriggerContext(final Map<String, Object> variables, final TriggerSystem triggerSystem) {
        this.variables = variables;
        this.triggerSystem = triggerSystem;
    }

    public String resolve(String template) {
        var result = template;
        for (final var entry : variables.entrySet()) {
            result = result.replace("${" + entry.getKey() + "}", entry.getValue().toString());
        }
        return result;
    }
}
