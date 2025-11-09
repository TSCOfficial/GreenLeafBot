package ch.greenleaf.interactions.actions.list;

import ch.greenleaf.interactions.InteractionContext;
import ch.greenleaf.interactions.actions.ActionManager;

public abstract class Action {
	
	protected ActionManager actionManager;
	
	protected InteractionContext ctx;
	
	public Action() {
	
	}
	
	/**
	 * Default constructor using action manager and the context
	 * @param actionManager Action manager
	 * @param ctx Interaction context (event)
	 */
	public Action(ActionManager actionManager, InteractionContext ctx) {
		this.actionManager = actionManager;
		this.ctx = ctx;
		fetchDatabase();
		execute();
	}
	
	public Action(InteractionContext ctx) {
		this.ctx = ctx;
	}
	
	protected abstract void fetchDatabase();
	
	protected abstract void execute();
}
