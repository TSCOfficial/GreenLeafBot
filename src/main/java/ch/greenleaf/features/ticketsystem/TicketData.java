package ch.greenleaf.features.ticketsystem;

import ch.greenleaf.Database;
import ch.greenleaf.template.embed.Field;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.awt.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class TicketData {

    private ButtonInteractionEvent event;

    // System info for creating correct Ticket
    private String guildId;

    // Ticket Channel
    private String panelChannel; // TODO replace later with datatype TextChannel
    private TextChannel ticketChannel;
    private ArrayList<TicketFormats> ticketFormats = new ArrayList<>();

    private String formatTicketValue;
    private String formatCustomTextValue;

    // Ticket Panel
    private boolean isClosed;
    private Color color;
    private String title;
    private String descriptiption;
    private List<Field> fields = new ArrayList<>();
    private String imageUrl;
    private String thumbnailUrl;
    private String author;
    private String authorIcon;
    private String footer;
    private String footerIcon;
    private LocalDateTime timestamp;

    private String replyWhenTicketCreated;
    private String replyWhenTicketUnavailable;

    // In-Ticket Message
    private String welcomeToTicketMessage;



    TicketData(ButtonInteractionEvent event){
        this.event = event;
        this.guildId = event.getGuild().getId();

        fetchDatabase();
    }

    TicketData(SlashCommandInteractionEvent event){
        //this.event = event;
        this.guildId = event.getGuild().getId(); // TODO replace later with selected guild on the webinterface
        fetchDatabase();
    }


    ButtonInteractionEvent getEvent(){
        return event;
    }

    void setEvent(ButtonInteractionEvent event){
        this.event = event;
    }

    String getGuildId(){
        return guildId;
    }

    String getPanelChannel(){
        return panelChannel;
    }

    TextChannel getTicketChannel(){
        return ticketChannel;
    }

    void setTicketChannel(TextChannel ticketChannel){
        this.ticketChannel = ticketChannel;
    }

    ArrayList<TicketFormats> getTicketFormats() {
        return ticketFormats;
    }

    String getFormatTicketValue(){
        return formatTicketValue;
    }

    String getFormatCustomTextValue(){
        return formatCustomTextValue;
    }

    boolean isClosed(){return isClosed;}

    Color getColor(){
        return color;
    }

    String getTitle(){
        return title;
    }

    String getDescription(){
        return descriptiption;
    }

    List<Field> getFields(){
        return fields;
    }

    String getImageUrl(){
        return imageUrl;
    }

    String getThumbnailUrl(){
        return thumbnailUrl;
    }

    String getAuthor(){
        return author;
    }

    String getAuthorIcon(){
        return authorIcon;
    }

    String getFooter(){
        return footer;
    }

    String getFooterIcon(){
        return footerIcon;
    }

    LocalDateTime getTimestamp(){
        return timestamp;
    }

    String getReplyWhenTicketCreated(){
        return replyWhenTicketCreated;
    }

    String getReplyWhenTicketUnavailable(){
        return replyWhenTicketUnavailable;
    }

    String getWelcomeToTicketMessage(){
        return welcomeToTicketMessage + " " + event.getUser().getAsMention();
    }



    private void fetchDatabase(){
        Database.connect();

        ticketFormats.add(TicketFormats.TICKET);
        ticketFormats.add(TicketFormats.USERNAME);

        formatTicketValue = "0000";
        formatCustomTextValue = "";
        welcomeToTicketMessage = "Welcome to your Ticket!";

        guildId = "1228461292440780801";
        panelChannel = "1228462878378430499";

        isClosed = true;
        color = Color.GREEN;
        title = "Ticket Support";
        descriptiption = "Idk sumth like that yk";
        fields.add(new Field("Smth", "Ticket option or smth!", true));
        fields.add(new Field("Smth else", "Ticket option and all yk!", true));

        imageUrl = null;
        thumbnailUrl = null;
        author = "Ronny";
        footer = "Created by Ronny";
        timestamp = LocalDateTime.now(Clock.systemDefaultZone());

        replyWhenTicketUnavailable = "This ticket can not be opend. The Modteam closed it.";
        replyWhenTicketCreated = "View your ticket ${ticket}.";


    }
}
