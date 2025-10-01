package ch.greenleaf.interactions;

import ch.greenleaf.interactions.actions.ActionList;
import ch.greenleaf.interactions.actions.InteractionAction;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.requests.restaction.WebhookMessageCreateAction;
import org.jetbrains.annotations.NotNull;

public class SlashContext
        extends ListenerAdapter
        implements InteractionContext {

    private final SlashCommandInteractionEvent event;

    public SlashContext(SlashCommandInteractionEvent event) {
        this.event = event;
    }

    /**
     * React to Slashcommand interactions
     * @param event
     */
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        InteractionContext ctx = new SlashContext(event);

        InteractionAction action = ActionList.SEND_MESSAGE; // Beispiel
        //action.execute(ctx);
    }


    @Override
    public Integer getInteractionId() {
        // Beispiel: /sendmessage id:10
        return event.getOption("id").getAsInt();
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
	
	@Override
	public Member getAuthor() {
		return event.getMember();
	}
	
	@Override
	public Guild getGuild() {
		return event.getGuild();
	}
}

