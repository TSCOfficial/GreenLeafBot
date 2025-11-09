package ch.greenleaf.interactions;

import ch.greenleaf.DatabaseQuery;
import ch.greenleaf.Table;
import ch.greenleaf.interactions.actions.ActionManager;
import ch.greenleaf.components.message.Message;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.*;

/**
 * This context applies to Buttons, allowing them to execute an action by resolving the registered actions from its given button ID that is saved in the database.
 */
public class ButtonContext
        extends ListenerAdapter
        implements InteractionContext {

    private ButtonInteractionEvent event = null;

    private final List<ActionManager> actions = new ArrayList<>();

    /**
     * React to Button interactions<br>
     * @param event The interaction event
     */
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        this.event = event;
        try {
            ResultSet rs = new DatabaseQuery(Table.Button.SELF)
				.select()
				.join(
					DatabaseQuery.JoinType.INNER,
					Table.ButtonAction.SELF,
					Table.define(Table.Button.SELF, Table.Button.ID), DatabaseQuery.Operator.EQUALS,  Table.ButtonAction.BUTTON_ID)
				.where(Table.ButtonAction.BUTTON_ID, DatabaseQuery.Operator.EQUALS, getInteractionId())
				.executeQuery();
			
            while (rs.next()) {
				String action_id = rs.getString(Table.ButtonAction.ACTION_ID);
				System.out.println(action_id);
				
				ActionManager action = new ActionManager().getById(action_id);
				
                actions.add(action);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        actions.forEach(action -> action.execute(this));
        System.out.println("Alle aktionen ausgeführt");
        actions.clear(); // Due that this class stays the same all the online time of the bot, after each Interaction, the used action list needs to be cleared!
    }


    /**
     * Get the ID of the button
     * @return The button ID
     */
    @Override
    public String getInteractionId() {
        return event.getButton().getId();
    }

    @Override
    public long getChannelId() {
        return event.getChannel().getIdLong();
    }

    @Override
    public void reply(InteractionResponse response) {
		Message msg = response.message();
        ReplyCallbackAction action = event.reply(msg.getText()).setEphemeral(msg.isEphemeral()).addEmbeds(msg.getEmbeds());
        action.queue();
    }
	
	@Override
	public void edit(InteractionResponse response) {
	}

    @Override
    public void send(InteractionResponse response) {
		Message msg = response.message();
        TextChannel channel = event.getJDA().getTextChannelById(msg.getChannelId());
        MessageCreateAction action = channel.sendMessage(msg.getText());
		action.setEmbeds(response.message().getEmbeds());
		// todo implement all other message stuff
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
