package ch.greenleaf.interaction;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

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
    public void reply(String message) {
        event.getHook().sendMessage(message).queue();
    }

    @Override
    public void sendToChannel(Long channelId, String message) {
        event.getJDA().getTextChannelById(channelId).sendMessage(message).queue();
    }
}

