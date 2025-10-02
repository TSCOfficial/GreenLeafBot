package ch.greenleaf.interactions.actions.list;

import ch.greenleaf.DatabaseQuery;
import ch.greenleaf.Table;
import ch.greenleaf.interactions.InteractionContext;
import ch.greenleaf.interactions.InteractionResponse;
import ch.greenleaf.interactions.actions.Action;
import ch.greenleaf.template.embed.Embed;
import ch.greenleaf.template.message.Message;

import java.sql.ResultSet;

public class SendMessage{
	
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
			
            rs.next();
			
			System.out.println(action.getDatasourceTable());
			
			// Get values
			String text = rs.getString(Table.Message.TEXT);
			boolean isEphemeral = rs.getBoolean(Table.Message.IS_EPHEMERAL);
			long channelId = rs.getLong(Table.Message.CHANNEL_ID);
			
			// Implement values in message object
            message.setText(text);
            message.setEphemeral(isEphemeral);
			message.setChannelId(channelId);
			
			System.out.println("Created message with text: " + text);
			
			// Append all connected embeds
			do {
				int embed_id = rs.getInt(Table.MessageEmbed.EMBED_ID);
				
				if (embed_id != 0) {
					Embed embed = new Embed().getById(embed_id);
					message.addEmbed(embed);
				}
			} while (rs.next());

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
                    new InteractionResponse.Builder(message.getText())
						.sendInChannel(message.getChannelId())
						.setEmbeds(message.getEmbeds())
						.build()
            );

        } else {
            ctx.reply(
                    new InteractionResponse.Builder(message.getText())
						.isEphemeral(message.isEphemeral())
						.setEmbeds(message.getEmbeds())
						.build()
            );
        }
    }
}

