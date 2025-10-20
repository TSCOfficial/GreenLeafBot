package ch.greenleaf.interactions.actions.list;

import ch.greenleaf.DatabaseQuery;
import ch.greenleaf.Table;
import ch.greenleaf.interactions.InteractionContext;
import ch.greenleaf.interactions.InteractionResponse;
import ch.greenleaf.interactions.Resolver;
import ch.greenleaf.interactions.actions.Action;
import ch.greenleaf.template.embed.Embed;
import ch.greenleaf.template.message.Message;
import net.dv8tion.jda.api.entities.channel.Channel;

import java.sql.ResultSet;

/**
 * Send a message
 */
public class SendMessage{
	
	public enum Variable {
		CHANNEL_ID,
		MESSAGE_TEXT
	}
	
	// Action endpoint ID
	public static final String ID = "/message/send";

	// The action that contains the action data (action table and id)
    private final Action action;
	
	// The Interaction context (i.e. Button, Slash, ...)
    private final InteractionContext ctx;
	
	// The message object
	private Message message = new Message();

    /**
     * <h1>Message action</h1>
     * Send a custom message
     * @param action The connected action containing the required action database table and datasource id
     * @param ctx The interaction context, treating the actions differently if the interaction event comes from a Button, a Slashcommand or others
     */
    public SendMessage(Action action, InteractionContext ctx) {
        this.action = action;
        this.ctx = ctx;
        fetchDatabase();
        execute();
    }

    /**
     * Get all needed data from the database
     */
    private void fetchDatabase() {
        try {
			ResultSet rs = new DatabaseQuery(action.getDatasourceTable())
				.join(
					DatabaseQuery.JoinType.LEFT, // LEFT JOIN REQUIRED! Even if a message does not have an embed (therefore not in message_embed) it should still be outputted in that query (left join = All left + matching existing right)
					Table.MessageEmbed.SELF,
					Table.Message.ID, DatabaseQuery.Operator.EQUALS, Table.MessageEmbed.MESSAGE_ID
				)
				.where(Table.Message.ID, DatabaseQuery.Operator.EQUALS, action.getDatasourceId())
				.executeQuery();
			
			// Append all connected embeds
			while (rs.next()) {
				System.out.println(action.getDatasourceTable());
				
				// Get values
				String text = rs.getString(Table.Message.TEXT);
				boolean isEphemeral = rs.getBoolean(Table.Message.IS_EPHEMERAL);
				long channelId = rs.getLong(Table.Message.CHANNEL_ID);
				
				// Overwrite database values with variable input (slashCommand options)
				Channel opt_channel = Resolver.resolveChannel(ctx, action, Variable.CHANNEL_ID.name(), channelId);
				String opt_message_text = Resolver.resolveString(ctx, action, Variable.MESSAGE_TEXT.name(), text);
				
				// Implement values in message object
				message.setText(opt_message_text);
				message.setEphemeral(isEphemeral);
				message.setChannelId(opt_channel.getIdLong()); // todo error: opt_channel is null ?!?
				// todo possible causes: wrong database inserts, fetch in Resolver -> getOption not 100% correct
				
				System.out.println("Created message with text: " + opt_message_text);
				
				int embed_id = rs.getInt(Table.MessageEmbed.EMBED_ID);
				
				if (embed_id != 0) {
					Embed embed = new Embed().getById(embed_id);
					message.addEmbed(embed);
				}
				/* current problem: When multiple action have the same action type, the action code saves the previous settings except overwritten (Here: message stays the same object)
				But if I reset everything at the end of the loop, the embeds wont be able to be appended to the same messages if there are multiple!
				 */
			}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Execute the action
     */
    private void execute() {
        if (message.getChannelId() != 0) {
            ctx.sendToChannel(
                    new InteractionResponse.Builder().message(message)
						.build()
            );

        } else {
            ctx.reply(
                    new InteractionResponse.Builder().message(message)
						.build()
            );
        }
    }
}

