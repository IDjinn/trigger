import idjinn.tools.TriggerContext;
import idjinn.tools.TriggerSystem;
import idjinn.tools.events.defaults.UnknownEvent;
import org.junit.jupiter.api.Test;

public class TriggerTests {
    @Test
    public void test_single_trigger_structure() {
        final var XML = """
                <?xml version="1.0" encoding="UTF-8"?>
                <tns:database xmlns:tns="http://www.iw.com/sns/platform/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                  <trigger id="1" type="1" name="trigger test">
                    <events>
                        <event type="0" name="hello world"/>
                    </events>
                    <conditions>
                        <condition type="1" name="simple condition">
                            <value>10</value>
                                <op>==</op>
                            <value>10</value>
                        </condition>
                    </conditions>
                    <actions>
                        <action type="1" name="printf">
                            <value>${hello}</value>
                        </action>
                    </actions>
                  </trigger>
                </tns:database>
                """;

        final var triggerSystem = new TriggerSystem("idjinn.tools");
        final var elements = triggerSystem.parseXMLSource(XML);
        triggerSystem.init(elements);

        final var triggerContext = new TriggerContext(triggerSystem);
        triggerContext.getVariables().put("hello", "world");
        triggerSystem.onEvent(triggerContext, new UnknownEvent("building created"));
    }
}
