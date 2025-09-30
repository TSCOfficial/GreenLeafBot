package ch.greenleaf.interactions.actions;

import ch.greenleaf.interactions.InteractionResponse;
import ch.greenleaf.interactions.actions.list.SendMessage;
import ch.greenleaf.template.embed.Embed;
import ch.greenleaf.template.embed.Field;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.List;

public class ActionList {

    public static final InteractionAction SEND_MESSAGE = SendMessage::new;
	
    public static final InteractionAction ADD_ROLE = (action, ctx) -> {
        // Hier kannst du aus ctx z.B. die Member-ID oder Guild-ID auslesen
        // Für JDA brauchst du ggf. spezielle Methoden in deinem Context,
        // wenn du Zugriff auf Guild oder Member willst.
    };

    public static final InteractionAction REMOVE_ROLE = (action, ctx) -> {
        // wie ADD_ROLE, nur andersherum :)
    };
}
