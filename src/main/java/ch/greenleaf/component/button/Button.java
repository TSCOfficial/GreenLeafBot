package ch.greenleaf.component.button;

import net.dv8tion.jda.api.entities.emoji.EmojiUnion;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Create a Button object.<br>
 * To add this Button to a Discord Message, use
 * {@code ButtonManager.build(button)} in einer {@code actionrow}
 */
public class Button {

    ButtonActionHandler buttonHandler = new ButtonActionHandler();

    // Default values
    private String buttonLabel = "Open Ticket";
    private boolean isDisabled = false;
    private ButtonStyle buttonStyle = ButtonStyle.SECONDARY;
    private EmojiUnion buttonEmoji = null;
    private String buttonId = "0";
    private List<ButtonAction> actions = new ArrayList<>();

    public void setLabel(String label) {
        buttonLabel = label;
    }

    public String getLabel() {
        return buttonLabel;
    }

    public void setEmoji(EmojiUnion emoji) {
        buttonEmoji = emoji;
    }

    public EmojiUnion getEmoji() {
        return buttonEmoji;
    }

    public void setStyle(ButtonStyle buttonStyle) {
        this.buttonStyle = buttonStyle;
    }

    public ButtonStyle getStyle() {
        return buttonStyle;
    }

    public void setId(String id) {
        buttonId = id;
    }

    public String getId() {
        return buttonId;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public List<ButtonAction> getActions() {
        return actions;
    }

    public void setActions(List<ButtonAction> actions) {
        this.actions = actions;
    }

    /**
     * Gets triggered when a Button is pressed
     * @param event the {@link ButtonInteractionEvent}
     */
    public void action(@NotNull ButtonInteractionEvent event) {
//        TicketManager manager = new TicketManager();
//        manager.createTicket(event);
        buttonHandler.action(event, actions);
    }

}
