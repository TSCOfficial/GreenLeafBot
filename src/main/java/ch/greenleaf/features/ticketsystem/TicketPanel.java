package ch.greenleaf.features.ticketsystem;

import ch.greenleaf.Client;
import ch.greenleaf.component.button.Button;
import ch.greenleaf.component.button.ButtonActions;
import ch.greenleaf.component.button.ButtonManager;
import ch.greenleaf.template.embed.Embed;
import ch.greenleaf.template.embed.EmbedManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

import java.util.List;

class TicketPanel {

    private TicketData data;

    TicketPanel(TicketData data){
        this.data = data;

        createPanel();
    }

    void createPanel(){
        Guild guild = Client.client.getShardManager().getGuildById(data.getGuildId());
        assert guild != null;
        TextChannel channelTicketPanel = guild.getTextChannelById(data.getPanelChannel());

        Embed embed = new Embed();
        embed.setTitle(data.getTitle());
        embed.setDescription(data.getDescription());
        embed.setColor(data.getColor());
        embed.setImageUrl(data.getImageUrl());
        embed.setAuthor(data.getAuthor());
        embed.setFooter(data.getFooter());
        embed.setFields(data.getFields());
        embed.setTimestamp(data.getTimestamp());
        embed.setAuthorIcon(data.getAuthorIcon());
        embed.setFooterIcon(data.getFooterIcon());
        embed.setThumbnailUrl(data.getThumbnailUrl());
        embed.setEphemeral(false);


        Button button = new Button();
        button.setId("panelbtn-01");
        button.setLabel("Ticket Panel");
        button.setStyle(ButtonStyle.SECONDARY);
        button.setDisabled(false);

        button.setActions(List.of(ButtonActions.SEND_EMBED, ButtonActions.ADD_ROLE));

        ButtonManager buttonManager = Client.client.buttonManager;
        buttonManager.add(button); // TODO temporary: later fetch data directly from DB when interaction found


        assert channelTicketPanel != null;
        channelTicketPanel.sendMessage("")
                          .setEmbeds(EmbedManager.EmbedToMessageEmbed(embed))
                          .addActionRow(ButtonManager.build(button))
                          .queue();
    }

}
