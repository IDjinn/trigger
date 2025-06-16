# Trigger System

A configurable, event-driven, trigger-based state machine system designed for high flexibility and custom behavior execution. Configuration is XML-based and enables the definition of triggers, conditions, and actions in a declarative manner.

## Features

- **Event-Driven:** Triggers respond to named events.
- **Condition-Based:** Execution is conditional, based on logical expressions.
- **Action-Oriented:** Executes actions when conditions are met.
- **XML Configurable:** Fully configurable using structured XML input.
- **Flexible:** Suitable for various use cases (e.g., games, workflows, automation systems).

## Example

```java
public class TriggerSystemExample {

    public static void main(String[] args) {
        final var xmlConfiguration = """
                <?xml version="1.0" encoding="UTF-8"?>
                <tns:database xmlns:tns="http://www.iw.com/sns/platform/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                  <trigger id="create-user-admin-check" type="1" name="Admin User Created">
                    <events>
                        <event type="0" name="user_created"/>
                    </events>
                    <conditions>
                        <condition type="1" name="role is admin">
                            <value>${role}</value>
                            <op>==</op>
                            <value>admin</value>
                        </condition>
                    </conditions>
                    <actions>
                        <action type="1" name="welcomeAdmin">
                            <value>Welcome, administrator ${username}!</value>
                        </action>
                    </actions>
                  </trigger>
                </tns:database>
                """;

        final var triggerSystem = new TriggerSystem("idjinn.tools");
        final var elements = triggerSystem.parseXMLSource(xmlConfiguration);
        triggerSystem.init(elements);

        final var context = new TriggerContext(triggerSystem);
        context.getVariables().put("username", "alice");
        context.getVariables().put("role", "admin");

        triggerSystem.onEvent(context, new UnknownEvent("user_created"));
    }
}
```


## XML Schema Overview

- `<trigger>`: Defines an individual trigger.
  - `id`, `type`, `name`: Metadata.
  - `<events>`: One or more `<event>` elements that define what the trigger listens to.
  - `<conditions>`: Logical conditions to evaluate before executing actions.
  - `<actions>`: Executable logic that runs when all conditions pass.

## Requirements

- Java 17+
- JUnit (for testing)

## Usage

1. Define your trigger system using XML.
2. Initialize the system with `TriggerSystem.parseXMLSource`.
3. Call `onEvent()` with a context and event to activate triggers.

## License

MIT License
