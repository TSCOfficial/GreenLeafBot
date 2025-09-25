package ch.greenleaf.interaction.actions;

import ch.greenleaf.interaction.InteractionContext;
import ch.greenleaf.interaction.InteractionResponse;

public class SendMessage{

    private final InteractionContext ctx;
    private String message;
    private Long channelId;

    public SendMessage(InteractionContext ctx) {
        this.ctx = ctx;
        fetchDatabase();
        execute();
    }

    private void fetchDatabase() {
        String id = ctx.getInteractionId();
        // DB lookup hier (id → message, channelId)
        message = "Hello? You pushed me!";
        channelId = null;
    }

    private void execute() {
        if (channelId != null) {
            ctx.sendToChannel(
                    new InteractionResponse.Builder(message)
                            .sendInChannel(channelId)
                            .build()
            );
        } else {
            ctx.reply(
                    new InteractionResponse.Builder(message)
                            .build()
            );
        }
    }
}

