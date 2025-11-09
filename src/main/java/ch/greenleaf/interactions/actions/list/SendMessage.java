package ch.greenleaf.interactions.actions.list;

import ch.greenleaf.Client;
import ch.greenleaf.DatabaseQuery;
import ch.greenleaf.Table;
import ch.greenleaf.interactions.InteractionContext;
import ch.greenleaf.interactions.InteractionResponse;
import ch.greenleaf.interactions.Resolver;
import ch.greenleaf.interactions.actions.ActionManager;
import ch.greenleaf.components.embed.Embed;
import ch.greenleaf.components.message.Message;
import net.dv8tion.jda.api.entities.channel.Channel;

import java.sql.ResultSet;

/**
 * Send a message
 */
public class SendMessage extends Action{
	
	public enum Variable {
		CHANNEL_ID,
		MESSAGE_TEXT
	}
	
	// Action endpoint ID
	public static final String ID = "/message/send";
	
	// The message object
	private Message message;

    /**
     * <h1>Message action</h1>
     * Send a custom message
     * @param actionManager The connected action containing the required action database table and datasource id
     * @param ctx The interaction context, treating the actions differently if the interaction event comes from a Button, a Slashcommand or others
     */
    public SendMessage(ActionManager actionManager, InteractionContext ctx) {
        super(actionManager, ctx);
    }
	
	/**
	 * Constructor for features instead of actions
	 * @param message Message to be sent
	 */
	public SendMessage(Message message) {
		this.message = message;
		execute();
	}

    /**
     * Get all needed data from the database
     */
	@Override
    protected void fetchDatabase() {
        try {
			ResultSet rs = new DatabaseQuery(actionManager.getDatasourceTable())
				.join(
					DatabaseQuery.JoinType.LEFT, // LEFT JOIN REQUIRED! Even if a message does not have an embed (therefore not in message_embed) it should still be outputted in that query (left join = All left + matching existing right)
					Table.MessageEmbed.SELF,
					Table.Message.ID, DatabaseQuery.Operator.EQUALS, Table.MessageEmbed.MESSAGE_ID
				)
				.where(Table.Message.ID, DatabaseQuery.Operator.EQUALS, actionManager.getDatasourceId())
				.executeQuery();
			
			// Append all connected embeds
			while (rs.next()) {
				System.out.println("[FETCH action datasource table] " + actionManager.getDatasourceTable());
				
				// Get values
				String text = rs.getString(Table.Message.TEXT);
				boolean isEphemeral = rs.getBoolean(Table.Message.IS_EPHEMERAL);
				long channelId = rs.getLong(Table.Message.CHANNEL_ID);
				
				// Overwrite database values with variable input (slashCommand options)
				System.out.println("[FETCH text] " + text);
				System.out.println("[FETCH channelId] " + channelId);
				Channel opt_channel = Resolver.resolveChannel(ctx, actionManager, Variable.CHANNEL_ID.name(), channelId);
				String opt_message_text = Resolver.resolveString(ctx, actionManager, Variable.MESSAGE_TEXT.name(), text);
				
				System.out.println(opt_channel);
				
				message = new Message();
				message.setText(opt_message_text);
				message.setEphemeral(isEphemeral);
				
				if (opt_channel != null){ // interaction-reply messages does not have channel id
					message.setChannelId(opt_channel.getIdLong());
				}
				
				System.out.println("Created message with text: " + opt_message_text);
				
				int embed_id = rs.getInt(Table.MessageEmbed.EMBED_ID);
				
				if (embed_id != 0) {
					Embed embed = new Embed().getById(embed_id);
					message.addEmbed(embed);
				}
			}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Execute the action
     */
	@Override
    protected void execute() {
		if (ctx == null) {
			Client.client.getShardManager().getTextChannelById(message.getChannelId()).sendMessage("").addEmbeds(message.getEmbeds()).queue();
		} else{
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
}

