package ch.greenleaf.interactions.actions.list;

import ch.greenleaf.interactions.InteractionContext;
import ch.greenleaf.interactions.InteractionResponse;
import ch.greenleaf.template.embed.Embed;
import ch.greenleaf.template.embed.Field;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class SendEmbed {

    private final InteractionContext ctx;
    private String message;
    private Long channelId;

    public SendEmbed(InteractionContext ctx) {
        this.ctx = ctx;
        fetchDatabase();
        execute();
    }

    private void fetchDatabase() {
        Integer id = ctx.getInteractionId();
        // DB lookup hier (id → message, channelId)
        message = "Hello? You pushed me!";
        channelId = null;
    }

    private void execute() {
        // get button Id
        // fetch embed data from database using button id
        // create embed using the Embed Class and EmbedManager Class

        Embed embed = new Embed();
        embed.setText("Nachricht gesendet!");
        embed.setTitle("Nachricht gesendet!");
        embed.setDescription("Nachricht gesendet!");
        embed.setFooter("Nachricht gesendet!");
        embed.addField(new Field("Erfolg", "Nachricht gesendet!", true));
        embed.addField(new Field("Erfolg", "Nachricht gesendet!", true));
        embed.addField(new Field(null, null, false));
        embed.addField(new Field("Erfolg", "Nachricht gesendet!", true));

        MessageEmbed messageEmbed = embed.build();

//        TextChannel channel = event.getGuild().getTextChannelById(embed.getChannelId()); // TODO enable possibility not only Text Channels

        ctx.reply(
                new InteractionResponse.Builder(message)
                        //.addEmbed(messageEmbed)
                        .build()
        );
    }
}

