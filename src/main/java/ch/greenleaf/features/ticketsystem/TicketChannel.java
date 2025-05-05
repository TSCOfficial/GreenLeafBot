package ch.greenleaf.features.ticketsystem;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Manage Ticket Channels.<br>
 * Create a new one, set permissions, edit them
 */
class TicketChannel{

    private TicketChannel ticketInstance;
    private TicketData data;

    private ButtonInteractionEvent interactionEvent;

    TicketChannel(TicketData data){
        this.data = data;
    }

    /**
     * Creates a Ticket for a given {@link Member} through the {@link ButtonInteractionEvent}.
     *
     * @param interactionEvent The {@link ButtonInteractionEvent}.
     */
    TicketChannel setup(@NotNull ButtonInteractionEvent interactionEvent){
        System.out.println("2 Ticket Setup");

        this.interactionEvent = interactionEvent;

        return this;
    }

    /**
     *  Initiate the Channel creation Discord Action.
     *
     */
    void create(){
        System.out.println("3 Ticket creation");
        String channelName = resolve(data.getTicketFormats());

        this.interactionEvent.getGuild().createTextChannel(channelName)
             .setTopic("Support Ticket")
             .queue(channel -> {
                 data.setTicketChannel(channel);
                 System.out.println("Channel Id: " + channel.getIdLong());
                 new TicketPermission(this.interactionEvent, channel.getIdLong());
                 channel.sendMessage(data.getWelcomeToTicketMessage()).queue();
                 new Response(interactionEvent, data).buttonOpenResponse();

             });
    }

    String resolve(ArrayList<TicketFormats> ticketFormats){
        Member member = this.interactionEvent.getMember();
        StringBuilder channelnameFormat = new StringBuilder();
        for (TicketFormats format : ticketFormats){
            switch (format){
                case TicketFormats.TICKET -> {
                    channelnameFormat.append("ticket");
                }
                case TicketFormats.DIGIT -> {
                    channelnameFormat.append(data.getFormatTicketValue());
                }
                case TicketFormats.USERNAME -> {
                    channelnameFormat.append(member.getUser().getName());
                }
                case TicketFormats.CUSTOMTEXT -> {
                    channelnameFormat.append(data.getFormatCustomTextValue());
                }
            }
            channelnameFormat.append("-");
        }

        return channelnameFormat.toString();
    }


}
