import idjinn.tools.TriggerSystem;
import org.junit.jupiter.api.Test;

public class TriggerTests {
    @Test
    public void test_single_trigger_structure() {
        final var XML = """
                <?xml version="1.0" encoding="UTF-8"?>
                <tns:database xmlns:tns="http://www.iw.com/sns/platform/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                  <trigger id="1" type="1" name="trigger test">
                    <events>
                      <event id="1" type="1" name="hello world"/>
                    </events>
                    <conditions>
                      <condition id="1" type="1" name="simple condition">1==1</condition>
                    </conditions>
                    <actions>
                      <action id="1" type="1" name="printf">hello world!</action>
                    </actions>
                  </trigger>
                </tns:database>
                """;
        final var triggerSystem = new TriggerSystem();
        triggerSystem.init(triggerSystem.parseXMLSource(XML));
    }
}
