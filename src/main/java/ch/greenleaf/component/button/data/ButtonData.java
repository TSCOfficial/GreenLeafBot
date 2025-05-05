package ch.greenleaf.component.button.data;

import ch.greenleaf.component.button.ButtonAction;
import net.dv8tion.jda.api.entities.emoji.EmojiUnion;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

import java.util.ArrayList;
import java.util.List;

public class ButtonData {

    private String buttonId;

    private String label = "Click me!";
    private EmojiUnion emoji;
    private ButtonStyle style = ButtonStyle.PRIMARY;
    private boolean isDisabled = false;
    private List<ButtonAction> actions = new ArrayList<>();


    private ButtonData(ButtonInteractionEvent event) {
        this.buttonId = event.getButton().getId();
    }

    private void fetchDatabase(){
        
    }

    public EmojiUnion getEmoji() {
        return emoji;
    }

    public String getButtonId() {
        return buttonId;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public String getLabel() {
        return label;
    }

    public ButtonStyle getStyle() {
        return style;
    }

    public List<ButtonAction> getActions() {
        return actions;
    }

}
