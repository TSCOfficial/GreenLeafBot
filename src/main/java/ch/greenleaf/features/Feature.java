package ch.greenleaf.features;

import ch.greenleaf.interactions.InteractionContext;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.events.interaction.component.GenericComponentInteractionCreateEvent;

/**
 * <i>[!] Any instance fields of the subclasses remain null, due that the subclass is not initiated till after {@link #fetchDatabase()} and {@link #getTemplate()} is executed. Static fields can still be used and set directly outside the {@link #fetchDatabase()} due that they are initiated for the class itself, not for the instance.</i>
 */
public abstract class Feature {
	protected long guild_id;
	protected InteractionContext ctx;
	
	public Feature(long guild_id){
		this.guild_id = guild_id;
		fetchDatabase();
		getTemplate();
	}
	
	public Feature(InteractionContext ctx){
		this.ctx = ctx;
		this.guild_id = ctx.getGuild().getIdLong();
		fetchDatabase();
		getTemplate();
	}
	
	/**
	 * Load all data from the database
	 */
	protected abstract void fetchDatabase();
	
	/**
	 * Create the content of the feature
	 */
	protected abstract void getTemplate();
}
