package ch.greenleaf.features.ticketsystem;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class TicketManager {

    private TicketChannel ticketChannel;
    private TicketData data;

    public void sendPanel(SlashCommandInteractionEvent event){
        data = new TicketData(event);

        new TicketPanel(data);
    }

    public void createTicket(ButtonInteractionEvent event){
        data = new TicketData(event);

        if (data.isClosed()){
            new Response(event, data).buttonOpenResponse();
            return;
        }

        ticketChannel = new TicketChannel(data);
        ticketChannel.setup(event)
                     .create();

    }

    public void deleteTicket(){

    }

    public void editTicket(){

    }


    public TicketData getData(String id){
        return data;
    }
}
