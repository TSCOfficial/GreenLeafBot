package ch.greenleaf.interactions.actions;

import ch.greenleaf.interactions.InteractionContext;

@FunctionalInterface
public interface InteractionAction {
    void execute(Action action, InteractionContext ctx);
}

