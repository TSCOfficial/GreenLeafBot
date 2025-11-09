package ch.greenleaf.interactions.actions;

import ch.greenleaf.interactions.InteractionContext;

/**
 * Controls what arguments are passed to the supported action constructors
 */
@FunctionalInterface
public interface InteractionAction {
    void execute(Action action, InteractionContext ctx);
}

