package ch.greenleaf.interaction;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class ButtonContext implements InteractionContext {

    private final ButtonInteractionEvent event;

    public ButtonContext(ButtonInteractionEvent event) {
        this.event = event;
    }

    @Override
    public String getActionId() {
        return event.getButton().getId();
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
