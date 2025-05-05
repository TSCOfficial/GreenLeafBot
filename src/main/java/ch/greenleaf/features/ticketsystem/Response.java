package ch.greenleaf.features.ticketsystem;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class Response{

    private ButtonInteractionEvent event;
    private TicketData data;

    public Response(ButtonInteractionEvent event, TicketData data){
        this.event = event;
        this.data = data;
    }

    public void buttonOpenResponse(){
        String message;
        if (data.isClosed()){
            message = data.getReplyWhenTicketUnavailable();
        } else {
            message = data.getReplyWhenTicketCreated();
        }
        event.getHook().editOriginal(message).queue();}

}
