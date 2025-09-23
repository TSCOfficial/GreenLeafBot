package ch.greenleaf.interaction;

import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.List;

public interface InteractionContext {
    // Get the ID that refers to the Database record
    String getActionId();

    Long getChannelId();

    void reply(InteractionResponse response);

    void sendToChannel(InteractionResponse response);

}

