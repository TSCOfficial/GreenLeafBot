package ch.greenleaf.interactions;

import ch.greenleaf.DatabaseQuery;
import ch.greenleaf.Table;
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
				.select()
				.join(
					DatabaseQuery.JoinType.INNER,
					Table.ButtonAction.SELF,
					Table.define(Table.Button.SELF, Table.Button.ID), DatabaseQuery.Operator.EQUALS, Table.define(Table.ButtonAction.SELF, Table.ButtonAction.BUTTON_ID))
				.where(Table.define(Table.ButtonAction.SELF, Table.ButtonAction.BUTTON_ID), DatabaseQuery.Operator.EQUALS, getInteractionId())
				.executeQuery();
            
            System.out.println("Reading responses");
            

            while (rs.next()) {
				int action_id = rs.getInt(Table.ButtonAction.ACTION_ID);
				System.out.println(action_id);
				
				Action action = new Action().getById(action_id);
				
                actions.add(action);
            }

        } catch (Exception e) {
            System.out.println(e);
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
    public Integer getInteractionId() {
        return Integer.valueOf(event.getButton().getId());
    }

    @Override
    public Long getChannelId() {
        return event.getChannel().getIdLong();
    }

    @Override
    public void reply(InteractionResponse response) {
        ReplyCallbackAction action = event.reply(response.getMessage()).setEphemeral(response.isEphemeral()).addEmbeds(response.getEmbeds());

        action.queue();

    }

    @Override
    public void sendToChannel(InteractionResponse response) {
        TextChannel channel = event.getJDA().getTextChannelById(response.getChannelId());
        MessageCreateAction action = channel.sendMessage(response.getMessage());

        action.queue();
    }

}
