package ch.greenleaf.interactions.actions;

import ch.greenleaf.interactions.actions.list.AddRole;
import ch.greenleaf.interactions.actions.list.SendMessage;

public class ActionList {

    public static final InteractionAction SEND_MESSAGE = SendMessage::new;
	
    public static final InteractionAction ADD_ROLE = AddRole::new;
}
