package ch.greenleaf.interactions.actions;

import ch.greenleaf.interactions.InteractionResponse;
import ch.greenleaf.interactions.actions.list.AddRole;
import ch.greenleaf.interactions.actions.list.SendMessage;
import ch.greenleaf.template.embed.Embed;
import ch.greenleaf.template.embed.Field;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.List;

public class ActionList {

    public static final InteractionAction SEND_MESSAGE = SendMessage::new;
	
    public static final InteractionAction ADD_ROLE = AddRole::new;
}
