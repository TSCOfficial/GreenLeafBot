package ch.greenleaf.interaction.actions;

import ch.greenleaf.interaction.InteractionContext;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

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
        String id = ctx.getActionId();
        // DB lookup hier (id → message, channelId)
        message = "Hello? You pushed me!";
        channelId = null;
    }

    private void execute() {
        if (channelId != null) {
            ctx.sendToChannel(channelId, message);
        } else {
            ctx.reply(message);
        }
    }
}

