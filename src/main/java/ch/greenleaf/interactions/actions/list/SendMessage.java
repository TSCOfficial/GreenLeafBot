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

    private final Action action;
    private final InteractionContext ctx;
	private Message message = new Message();
    private Long channelId;

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
					DatabaseQuery.JoinType.INNER,
					Table.MessageEmbed.SELF,
					Table.Message.ID, DatabaseQuery.Operator.EQUALS, Table.MessageEmbed.MESSAGE_ID
				)
				.where(Table.Message.ID, DatabaseQuery.Operator.EQUALS, action.getDatasourceId())
				.executeQuery();
			
            rs.next();
			
			// Get values
			String text = rs.getString(Table.Message.TEXT);
			boolean isEphemeral = rs.getBoolean(Table.Message.IS_EPHEMERAL);
			Long embed_id = rs.getLong(Table.MessageEmbed.EMBED_ID);
			
			// Build the message itself
            message.setText(text);
            message.setEphemeral(isEphemeral);
			
			System.out.println(embed_id);
			
			if (embed_id != 0) {
				Embed embed = new Embed().generateById(embed_id);
				message.addEmbed(embed);
			}
			
			channelId = rs.getLong(Table.Message.CHANNEL_ID);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Execute the action
     */
    private void execute() {
        if (channelId != null && channelId != 0) {
            ctx.sendToChannel(
                    new InteractionResponse.Builder(message.getText())
						.sendInChannel(channelId)
						.setEmbeds(message.getEmbeds())
						.build()
            );

        } else {
            System.out.println("Message: " + message);
            ctx.reply(
                    new InteractionResponse.Builder(message.getText())
						.isEphemeral(message.isEphemeral())
						.setEmbeds(message.getEmbeds())
						.build()
            );
        }
    }
}

