package ch.greenleaf.component.button;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Enables the possibility to add any existing feature to any existing Button
 */
public class ButtonActionHandler {

    // Aktionen, die diesem Button zugewiesen sind
    private final List<ButtonAction> assignedActions = new ArrayList<>();

    // Methode, um Aktionen hinzuzufügen
    public void addAction(ButtonAction action) {
        assignedActions.add(action);
    }

    // Methode, die beim Button-Druck aufgerufen wird
    public void action(@NotNull ButtonInteractionEvent event, List<ButtonAction> assignedActions) {
        //event.deferReply(true).queue(); // prevents
        if (assignedActions.isEmpty()) {
            event.reply("No actions were found for this interaction.").setEphemeral(true).queue(); // Return error message when this Button does not have any action.
            return;
        }

        // Alle zugewiesenen Aktionen ausführen
        assignedActions.forEach(action -> action.execute(event));
    }

}
