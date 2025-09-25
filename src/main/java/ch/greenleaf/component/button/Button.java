package ch.greenleaf.component.button;

import ch.greenleaf.interaction.actions.Action;
import ch.greenleaf.interaction.actions.InteractionAction;
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

    private String label = "My Button";
    private boolean isDisabled = false;
    private ButtonStyle style = ButtonStyle.SECONDARY;
    private EmojiUnion emoji = null;
    private String id = "0";
    private List<InteractionAction> actions = new ArrayList<>();

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

    /**
     * Set the style by its enum contant ordinal (see original: {@link ButtonStyle})<br>
     * Button Styles:
     * <ul>
     * <li>1: PRIMARY, bluprle</li>
     * <li>2: SECONDARY, gray</li>
     * <li>3: SUCCESS, green</li>
     * <li>4: DANGER, red</li>
     * <li>5: LINK, for a URL</li>
     * </ul>
     * @param styleId enum constant ordinal (the style id)
     */
    public void setStyle(int styleId) {
        this.style = ButtonStyle.fromKey(styleId);
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

    public List<InteractionAction> getActions() {
        return actions;
    }

    public void setActions(List<InteractionAction> actions) {
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

}
