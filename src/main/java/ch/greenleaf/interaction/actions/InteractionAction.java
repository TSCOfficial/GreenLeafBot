package ch.greenleaf.interaction.actions;

import ch.greenleaf.interaction.InteractionContext;

@FunctionalInterface
public interface InteractionAction {
    void execute(Action action, InteractionContext ctx);
}

