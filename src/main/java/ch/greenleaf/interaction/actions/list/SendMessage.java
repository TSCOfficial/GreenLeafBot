package ch.greenleaf.interaction.actions.list;

import ch.greenleaf.Database;
import ch.greenleaf.interaction.InteractionContext;
import ch.greenleaf.interaction.InteractionResponse;
import ch.greenleaf.interaction.actions.Action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SendMessage{

    private final Action action;
    private final InteractionContext ctx;
    private String message;
    private Long channelId;
    private boolean isEphemeral;

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
            Connection conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT id, text, channel_id, is_ephemeral FROM " + action.getDatasourceTable()
                            + " WHERE id = ?"
            );
            System.out.println(action.getDatasourceId());
            stmt.setInt(1, action.getDatasourceId());
            System.out.println(stmt);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            message = rs.getString(FieldNames.TEXT);
            channelId = rs.getLong("channel_id");
            isEphemeral = rs.getBoolean("is_ephemeral");


            System.out.println(message);
            System.out.println(channelId);

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
                    new InteractionResponse.Builder(message)
                            .sendInChannel(channelId)
                            .build()
            );

        } else {
            System.out.println("Message: " + message);
            ctx.reply(
                    new InteractionResponse.Builder(message)
                            .isEphemeral(isEphemeral)
                            .build()
            );
        }
    }

    private static final class FieldNames {
        public static final String TEXT = "text";

        private FieldNames() {

        }
    }
}

