package ch.greenleaf.interactions.actions;

import java.util.EnumMap;
import java.util.Map;

/**
 * Usage example:
 * <pre><code>
 *     ActionType type = ActionType.SEND_MESSAGE;
 *
 *     // Get the action code
 *     InteractionAction action = ActionRegistry.get(type);
 *
 *     // Execute the action
 *     action.execute(ctx);
 * </code></pre>
 */
public class ActionRegistry {

    private static final Map<ActionType, InteractionAction> REGISTRY = new EnumMap<>(ActionType.class);

    static {
        // Hier die Verknüpfungen herstellen:
        REGISTRY.put(ActionType.SEND_MESSAGE, ActionList.SEND_MESSAGE);
        REGISTRY.put(ActionType.EDIT_MESSAGE, ActionList.SEND_EMBED);
        REGISTRY.put(ActionType.ASSIGN_ROLE, ActionList.ADD_ROLE);
        REGISTRY.put(ActionType.REMOVE_ROLE, ActionList.REMOVE_ROLE);
    }

    /**
     * Get the selected interaction action from the registry using the action tyep
     * @param type Corresponding action type
     * @return The selected interaction action code that can be executed
     */
    public static InteractionAction get(ActionType type) {
        InteractionAction action = REGISTRY.get(type);
        if (action == null) {
            throw new IllegalArgumentException("No action registered for type " + type);
        }
        return action;
    }
}

