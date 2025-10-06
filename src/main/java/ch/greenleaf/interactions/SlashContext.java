package ch.greenleaf.interactions;

import ch.greenleaf.DatabaseQuery;
import ch.greenleaf.Table;
import ch.greenleaf.interactions.actions.Action;
import ch.greenleaf.template.message.Message;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * <i>[!] Slash commands have a special kind of ID due that it's not possible to add a custom ID. Their ID is their full command name (command + subgroup + subcommand)</i>
 */
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
		System.out.println("SlashContext event");
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
				String action_id = rs.getString(Table.CommandAction.ACTION_ID);
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
    public String getInteractionId() {
        // Beispiel: /sendmessage id:10
        return event.getFullCommandName();
    }

    @Override
    public long getChannelId() {
        return event.getChannel().getIdLong();
    }

    @Override
    public void reply(InteractionResponse response) {
		Message msg = response.message();
		ReplyCallbackAction action = event.reply(msg.getText())
			.setEphemeral(msg.isEphemeral())
			.addEmbeds(msg.getEmbeds());
		action.queue();
    }
	
	@Override
	public void edit(InteractionResponse response) {}

    @Override
    public void sendToChannel(InteractionResponse response) {
		Message msg = response.message();
        TextChannel channel = event.getJDA().getTextChannelById(msg.getChannelId());
        MessageCreateAction action = channel.sendMessage(msg.getText());

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
	
	/**
	 * Get an option by its attributed action type variable key<br>
	 * The key is resolved back to the saved option name and the options value is read out
	 * @param variableKey Action variable key
	 * @return Value of the option
	 */
	@Override
	public OptionMapping getOption(String variableKey) {
		try {
			ResultSet rs = new DatabaseQuery(Table.ActionTypeVariable.SELF)
				.select()
				.join(
					DatabaseQuery.JoinType.INNER,
					Table.CommandOptionActionTypeVariable.SELF,
					Table.define(Table.ActionTypeVariable.SELF, Table.ActionTypeVariable.ID),
					DatabaseQuery.Operator.EQUALS,
					Table.CommandOptionActionTypeVariable.ACTION_TYPE_VARIABLE_ID
				)
				.join(
					DatabaseQuery.JoinType.INNER,
					Table.CommandOption.SELF,
					Table.CommandOptionActionTypeVariable.COMMAND_OPTION_ID,
					DatabaseQuery.Operator.EQUALS,
					Table.define(Table.CommandOption.SELF, Table.CommandOption.ID)
				)
				.where(
					Table.ActionTypeVariable.VARIABLE_KEY,
					DatabaseQuery.Operator.EQUALS,
					variableKey
				).executeQuery();
			
			rs.next();
			
			String optionName = rs.getString(Table.CommandOption.NAME);
			
			System.out.println(optionName);
			
			return event.getOption(optionName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

