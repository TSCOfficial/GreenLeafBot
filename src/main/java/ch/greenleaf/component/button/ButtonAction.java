package ch.greenleaf.component.button;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

@FunctionalInterface
public interface ButtonAction {
    void execute(ButtonInteractionEvent event);
}
