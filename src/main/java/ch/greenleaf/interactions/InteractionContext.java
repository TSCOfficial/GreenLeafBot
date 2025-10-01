package ch.greenleaf.interactions;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

public interface InteractionContext {
    /**
     * Get the ID of the Interaction element, that has the saved actions saved in the Database
     */
    long getInteractionId();

    long getChannelId();

    void reply(InteractionResponse response);

    void sendToChannel(InteractionResponse response);

	Member getAuthor();
	
	Guild getGuild();
}

