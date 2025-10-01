package ch.greenleaf.interactions.actions;

import java.util.EnumMap;
import java.util.Map;

/**
 * With the help of this Registry, it is possible to execute an action, that is selected by its enum {@link ActionType}
 *
 * Usage example:
 * <pre><code>
 *     // Set the action type
 *     ActionType type = ActionType.SEND_MESSAGE;
 *
 *     // Get the action execution code
 *     InteractionAction action = ActionRegistry.get(type);
 *
 *     // Execute the action
 *     action.execute(ctx);
 * </code></pre>
 */
public class ActionRegistry {

    private static final Map<ActionType, InteractionAction> REGISTRY = new EnumMap<>(ActionType.class);
	
    static {
        REGISTRY.put(ActionType.SEND_MESSAGE, ActionList.SEND_MESSAGE);
		REGISTRY.put(ActionType.ADD_ROLE, ActionList.ADD_ROLE);
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

