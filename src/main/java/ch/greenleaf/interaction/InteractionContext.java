package ch.greenleaf.interaction;

public interface InteractionContext {
    String getActionId();
    Long getChannelId();
    void reply(String message);
    void sendToChannel(Long channelId, String message);
}

