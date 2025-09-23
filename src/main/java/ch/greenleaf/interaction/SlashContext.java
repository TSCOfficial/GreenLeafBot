package ch.greenleaf.interaction;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.requests.restaction.WebhookMessageCreateAction;

public class SlashContext implements InteractionContext {

    private final SlashCommandInteractionEvent event;

    public SlashContext(SlashCommandInteractionEvent event) {
        this.event = event;
    }

    @Override
    public String getActionId() {
        // Beispiel: /sendmessage id:10
        return event.getOption("id").getAsString();
    }

    @Override
    public Long getChannelId() {
        return event.getChannel().getIdLong();
    }

    @Override
    public void reply(InteractionResponse response) {
        WebhookMessageCreateAction<Message> action = event.getHook().sendMessage(response.getMessage());

        if (response.isEphemeral()){
            action.setEphemeral(response.isEphemeral());
        }

        action.queue();

    }

    @Override
    public void sendToChannel(InteractionResponse response) {
        TextChannel channel = event.getJDA().getTextChannelById(response.getChannelId());
        MessageCreateAction action = channel.sendMessage(response.getMessage());

        action.queue();
    }
}

