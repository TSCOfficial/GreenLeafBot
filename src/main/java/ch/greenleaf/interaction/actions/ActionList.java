package ch.greenleaf.interaction.actions;

import ch.greenleaf.interaction.InteractionContext;
import ch.greenleaf.interaction.InteractionResponse;
import ch.greenleaf.interaction.actions.list.SendMessage;
import ch.greenleaf.template.embed.Embed;
import ch.greenleaf.template.embed.Field;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.List;

public class ActionList {

    public static final InteractionAction SEND_MESSAGE = SendMessage::new;

    public static final InteractionAction SEND_EMBED = (action, ctx) -> {
        Embed embed = new Embed();
        embed.setMessage("Nachricht gesendet!");
        embed.setTitle("Nachricht gesendet!");
        embed.setDescription("Nachricht gesendet!");
        embed.setFooter("Nachricht gesendet!");
        embed.addField(new Field("Erfolg", "Nachricht gesendet!", true));
        embed.addField(new Field("Erfolg", "Nachricht gesendet!", true));
        embed.addField(new Field(null, null, false));
        embed.addField(new Field("Erfolg", "Nachricht gesendet!", true));

        MessageEmbed messageEmbed = embed.build();

        ctx.reply(
                new InteractionResponse.Builder(embed.getMessage())
                        .sendInChannel(embed.getChannelId())
                        .setEmbeds(List.of(messageEmbed))
                        .isEphemeral(embed.isEphemeral())
                        .build()
        );
    };

    public static final InteractionAction ADD_ROLE = (action, ctx) -> {
        // Hier kannst du aus ctx z.B. die Member-ID oder Guild-ID auslesen
        // Für JDA brauchst du ggf. spezielle Methoden in deinem Context,
        // wenn du Zugriff auf Guild oder Member willst.
    };

    public static final InteractionAction REMOVE_ROLE = (action, ctx) -> {
        // wie ADD_ROLE, nur andersherum :)
    };
}
