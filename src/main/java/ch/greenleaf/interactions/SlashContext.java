package ch.greenleaf.interactions;

import ch.greenleaf.DatabaseQuery;
import ch.greenleaf.Table;
import ch.greenleaf.interactions.actions.Action;
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

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SlashContext
        extends ListenerAdapter
        implements InteractionContext {

    private SlashCommandInteractionEvent event;
	
	private final List<Action> actions = new ArrayList<>();
	
	/**
     * React to Slashcommand interactions
     * @param event
     */
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
		this.event = event;
		try {
			ResultSet rs = new DatabaseQuery(Table.Command.SELF)
				.select()
				.join(
					DatabaseQuery.JoinType.INNER,
					Table.CommandAction.SELF,
					Table.define(Table.Command.SELF, Table.Command.ID), DatabaseQuery.Operator.EQUALS, Table.define(Table.CommandAction.SELF, Table.CommandAction.COMMAND_ID))
				.where(Table.define(Table.CommandAction.SELF, Table.CommandAction.COMMAND_ID), DatabaseQuery.Operator.EQUALS, getInteractionId())
				.executeQuery();
			
			while (rs.next()) {
				int action_id = rs.getInt(Table.CommandAction.ACTION_ID);
				System.out.println(action_id);
				
				Action action = new Action().getById(action_id);
				
				actions.add(action);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		actions.forEach(action -> action.execute(this));
		System.out.println("Alle aktionen ausgeführt");
		actions.clear(); // Due that this class stays the same all the online time of the bot, after each Interaction, the used action list needs to be cleared!
    }


    @Override
    public long getInteractionId() {
        // Beispiel: /sendmessage id:10
        return event.getCommandIdLong();
    }

    @Override
    public long getChannelId() {
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

