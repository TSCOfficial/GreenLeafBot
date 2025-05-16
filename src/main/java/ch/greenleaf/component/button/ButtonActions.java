package ch.greenleaf.component.button;


import ch.greenleaf.template.embed.Embed;
import ch.greenleaf.template.embed.EmbedManager;
import ch.greenleaf.template.embed.Field;
import net.dv8tion.jda.api.entities.MessageEmbed;

/**
 * These are the possible actions a user can define for a button interaction.
 */
public class ButtonActions {

    public static final ButtonAction SEND_MESSAGE = event -> {
        String id = event.getButton().getId();
        // fetch Data with according Id
        event.getHook().sendMessage("Nachricht gesendet!").queue();
    };

    public static final ButtonAction SEND_EMBED = event -> {
        // get button Id
        // fetch embed data from database using button id
        // create embed using the Embed Class and EmbedManager Class

        Embed embed = new Embed();
        embed.setMessage("Nachricht gesendet!");
        embed.setTitle("Nachricht gesendet!");
        embed.setDescription("Nachricht gesendet!");
        embed.setFooter("Nachricht gesendet!");
        embed.addField(new Field("Erfolg", "Nachricht gesendet!", true));
        embed.addField(new Field("Erfolg", "Nachricht gesendet!", true));
        embed.addField(new Field(null, null, false));
        embed.addField(new Field("Erfolg", "Nachricht gesendet!", true));

        MessageEmbed messageEmbed = EmbedManager.EmbedToMessageEmbed(embed);

//        TextChannel channel = event.getGuild().getTextChannelById(embed.getChannelId()); // TODO enable possibility not only Text Channels
//
        event.reply(
                embed.getMessage())
                .setEmbeds(messageEmbed)
                .setEphemeral(embed.isEphemeral()).queue();
    };

    public static final ButtonAction ADD_ROLE = event -> {
        var member = event.getMember();
        var role = event.getGuild().getRoleById("1229327110275989514"); // Ersetze mit einer echten Role-ID
        if (member != null && role != null) {
            event.getGuild().addRoleToMember(member, role).queue();
        }
    };

    public static final ButtonAction REMOVE_ROLE = event -> {
        var member = event.getMember();
        var role = event.getGuild().getRoleById("ROLE_ID_HERE"); // Ersetze mit einer echten Role-ID
        if (member != null && role != null) {
            event.getGuild().removeRoleFromMember(member, role).queue();
        }
    };
}