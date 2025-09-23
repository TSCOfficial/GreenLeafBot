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
 *
 * @author Aaron Frily
 */
public class Button {

    ButtonActionHandler buttonHandler = new ButtonActionHandler();

    private String label = "My Button";
    private boolean isDisabled = false;
    private ButtonStyle style = ButtonStyle.SECONDARY;
    private EmojiUnion emoji = null;
    private String id = "0";
    private List<ButtonAction> actions = new ArrayList<>();

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setEmoji(EmojiUnion emoji) {
        this.emoji = emoji;
    }

    public EmojiUnion getEmoji() {
        return emoji;
    }

    public void setStyle(ButtonStyle buttonStyle) {
        this.style = buttonStyle;
    }

    public ButtonStyle getStyle() {
        return style;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
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

    public net.dv8tion.jda.api.interactions.components.buttons.Button build(){
        net.dv8tion.jda.api.interactions.components.buttons.Button buttonComponent = net.dv8tion.jda.api.interactions.components.buttons.Button.of(
                style,
                id,
                label,
                emoji
        ).withDisabled(isDisabled);
        return buttonComponent;
    }

    /**
     * Gets triggered when a Button is pressed
     * @param event the {@link ButtonInteractionEvent}
     */
    public void action(@NotNull ButtonInteractionEvent event) {
        buttonHandler.action(event, actions);
    }

}
