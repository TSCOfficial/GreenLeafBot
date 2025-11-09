package ch.greenleaf.interactions.actions;

import ch.greenleaf.interactions.actions.list.AddRole;
import ch.greenleaf.interactions.actions.list.SendMessage;

/**
 * <h1>List of supported actions</h1>
 * Allows the system to use the actions using the enum variables.<br>
 * <br>
 * <i>[!] Constructor parameter for the actions are given via the {@link InteractionAction} functional interface.</i>
 */
public class ActionList {

    public static final InteractionAction SEND_MESSAGE = SendMessage::new;
	
    public static final InteractionAction ADD_ROLE = AddRole::new;
}
