package ch.greenleaf.component.button;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.ArrayList;
import java.util.List;

public class ButtonManager extends ListenerAdapter {

    private List<ch.greenleaf.component.button.Button> buttons = new ArrayList<>();

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        System.out.println("Button pressed: " + event.getButton().getId());
        System.out.println("Current saved buttons: " + buttons);
        String buttonId = event.getButton().getId();
        for (ch.greenleaf.component.button.Button button : buttons){ // TODO fetch data directly from database (DTO mapping)
            System.out.println(buttonId);
            System.out.println(event.getButton().getId());
            if (buttonId.equals(event.getButton().getId())){
                System.out.println("same ID");
                button.action(event);
                return;
            }
        }
    }

    public static Button build(ch.greenleaf.component.button.Button button){
        Button buttonComponent = Button.of(
                button.getStyle(),
                button.getId(),
                button.getLabel(),
                button.getEmoji()
        ).withDisabled(button.isDisabled());
        return buttonComponent;
    }

    public void add(ch.greenleaf.component.button.Button button) {
        buttons.add(button);
    }

}
