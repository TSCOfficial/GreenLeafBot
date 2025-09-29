package ch.greenleaf.interactions;

public interface InteractionContext {
    /**
     * Get the ID of the Interaction element, that has the saved actions saved in the Database
     */
    Integer getInteractionId();

    Long getChannelId();

    void reply(InteractionResponse response);

    void sendToChannel(InteractionResponse response);

}

