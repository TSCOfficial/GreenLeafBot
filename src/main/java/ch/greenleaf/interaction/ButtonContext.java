package ch.greenleaf.interaction;

import ch.greenleaf.Database;
import ch.greenleaf.interaction.actions.Action;
import ch.greenleaf.interaction.actions.ActionList;
import ch.greenleaf.interaction.actions.InteractionAction;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.requests.restaction.WebhookMessageCreateAction;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.*;

public class ButtonContext
        extends ListenerAdapter
        implements InteractionContext {

    private ButtonInteractionEvent event = null;

    private final List<Action> actions = new ArrayList<>();

    /**
     * React to Button interactions
     * @param event The interaction event
     */
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        this.event = event;

        // DB Lookup: welche Action ist mit diesem Button verknüpft?
        //InteractionAction action = ActionList.SEND_MESSAGE; // Beispiel, aus DB laden

        try {
            Connection conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement(
                    """
                            SELECT * FROM button AS btn
                            INNER JOIN button_action btn_act ON btn.id = btn_act.button_id
                            INNER JOIN action act ON btn_act.action_id = act.id
                            WHERE btn_act.button_id = ?
                            """
            );
            stmt.setInt(1, getInteractionId());
            System.out.println(stmt.toString());

            try (var rs = stmt.executeQuery()) {
                System.out.println("Reading responses");
                System.out.println(rs.getMetaData());

                ResultSetMetaData meta = rs.getMetaData();
                int columnCount = meta.getColumnCount();

                while (rs.next()) {
                    System.out.println("Response: ");

                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = meta.getColumnLabel(i); // oder getColumnName(i)
                        Object value = rs.getObject(i);
                        row.put(columnName, value);
                    }
                    System.out.println(row);
                    actions.add(new Action(
                            rs.getInt("id"),
                            rs.getInt("type"),
                            rs.getInt("datasource_id")
                    ));
                }
            } catch (Exception e) {
                System.out.println(e);
            }

            // Based on the saved actiontype,the correct action-table will be resolved
        } catch (Exception e) {
            System.out.println(e);
        }


        actions.forEach(action -> action.execute(this));
        System.out.println("Alle aktionen ausgeführt");
    }


    /**
     * Get the ID of the button
     * @return The button ID
     */
    @Override
    public Integer getInteractionId() {
        return Integer.valueOf(event.getButton().getId());
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

}
