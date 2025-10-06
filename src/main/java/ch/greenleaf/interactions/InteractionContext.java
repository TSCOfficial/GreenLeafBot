package ch.greenleaf.interactions;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.Optional;

public interface InteractionContext {
    /**
     * Get the ID of the Interaction element, that has the saved actions saved in the Database
     */
    String getInteractionId();

    long getChannelId();

    void reply(InteractionResponse response);
	
	void edit(InteractionResponse response);

    void sendToChannel(InteractionResponse response);

	Member getAuthor();
	
	Guild getGuild();
	
	default OptionMapping getOption(String variableKey) {
		return null;
	}
}

