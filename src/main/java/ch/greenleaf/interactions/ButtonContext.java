package ch.greenleaf.interactions;

import ch.greenleaf.DatabaseQuery;
import ch.greenleaf.interactions.actions.Action;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
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
            ResultSet rs = new DatabaseQuery("button")
				.select("action.id", "type", "datasource_id")
				.join(
					DatabaseQuery.JoinType.INNER,
					"button_action",
					"button.id", DatabaseQuery.Operator.EQUALS, "button_action.button_id")
				.join(
					DatabaseQuery.JoinType.INNER,
					"action",
					"button_action.action_id", DatabaseQuery.Operator.EQUALS, "action.id" )
				.where("button_action.button_id", DatabaseQuery.Operator.EQUALS, getInteractionId())
				.executeQuery();
            
            System.out.println("Reading responses");
            System.out.println(rs.getMetaData());

            while (rs.next()) {
                System.out.println("Response: ");
				
                System.out.println(rs.getInt("id"));
                System.out.println(rs.getInt("type"));
                System.out.println(rs.getInt("datasource_id"));
                actions.add(new Action(
                        rs.getInt("id"),
                        rs.getInt("type"),
                        rs.getInt("datasource_id")
                ));
            }

        } catch (Exception e) {
            System.out.println(e);
        }


        actions.forEach(action -> action.execute(this));
        System.out.println("Alle aktionen ausgeführt");
        actions.clear();
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
        ReplyCallbackAction action = event.reply(response.getMessage()).setEphemeral(response.isEphemeral());

//        if (response.isEphemeral()){
//            action.setEphemeral(response.isEphemeral());
//        }

        action.queue();

    }

    @Override
    public void sendToChannel(InteractionResponse response) {
        TextChannel channel = event.getJDA().getTextChannelById(response.getChannelId());
        MessageCreateAction action = channel.sendMessage(response.getMessage());

        action.queue();
    }

}
