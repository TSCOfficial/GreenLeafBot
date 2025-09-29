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

    public SendMessage(Action action, InteractionContext ctx) {
        this.action = action;
        this.ctx = ctx;
        fetchDatabase();
        execute();
    }

    private void fetchDatabase() {
        try {
            Connection conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement(
                    """
                            SELECT id, text, channel_id FROM ?
                            WHERE id = ?
                            """
            );
            System.out.println(action.getDatasourceTable());
            System.out.println(action.getDatasourceId());
            stmt.setString(1, action.getDatasourceTable());
            stmt.setInt(2, action.getDatasourceId());
            System.out.println(stmt);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            message = rs.getString("text");
            channelId = rs.getLong("channel_id");

            System.out.println(message);
            System.out.println(channelId);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void execute() {
        if (channelId != null) {
            ctx.sendToChannel(
                    new InteractionResponse.Builder(message)
                            .sendInChannel(channelId)
                            .build()
            );
        } else {
            System.out.println("Message: " + message);
            ctx.reply(
                    new InteractionResponse.Builder(message)
                            .build()
            );
        }
    }
}

