import idjinn.tools.TriggerFactory;
import idjinn.tools.TriggerSystem;
import idjinn.tools.events.Event;
import org.junit.jupiter.api.Test;

public class TriggerTests {
    @Test
    public void test_single_trigger_structure() {
        final var XML = """
                <?xml version="1.0" encoding="UTF-8"?>
                <tns:database xmlns:tns="http://www.iw.com/sns/platform/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                  <trigger id="1" type="1" name="trigger test">
                    <events>
                      <event type="1" name="hello world"/>
                              <event type="6" name="test building"/>
                    </events>
                    <conditions>
                              <condition type="1" name="simple condition"><value>10</value><op>==</op><value>10</value></condition>
                    </conditions>
                    <actions>
                              <action type="1" name="printf">hello world!</action>
                    </actions>
                  </trigger>
                </tns:database>
                """;

        TriggerFactory.registerPackage("idjinn.tools");
        final var triggerSystem = new TriggerSystem();
        final var elements = triggerSystem.parseXMLSource(XML);
        triggerSystem.init(elements);

        triggerSystem.onEvent(new Event(EventType.BUILDING_CREATED.getType(), "building created").addAttribute("BUILDING_ID", "1"));
    }
}
