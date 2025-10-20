package ch.greenleaf.interactions;

import ch.greenleaf.interactions.actions.Action;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.Channel;

public class Resolver {
	
	public static Role resolveRole(InteractionContext ctx, Action action, String optionName, long fallbackRoleId) {
		System.out.println(optionName);
		System.out.println(fallbackRoleId);
		
		String actionId = action.getId();
		// Check if options return anything (slash command mey return smth, buttons always return null)
		Role optRole = ctx.getOption(actionId, optionName) != null ? ctx.getOption(actionId, optionName).getAsRole() : null;
		
		
		if (optRole != null) {
			return optRole;
		}
		
		System.out.println("No option found");
		
		// Fallback: get action data
		return ctx.getGuild().getRoleById(fallbackRoleId);
	}
	
	public static Channel resolveChannel(InteractionContext ctx, Action action, String optionName, long fallbackChannelId) {
		System.out.println(optionName);
		System.out.println(fallbackChannelId);
		String actionId = action.getId();
		
		// Check if options return anything (slash command mey return smth, buttons always return null)
		Channel optRole = ctx.getOption(actionId, optionName) != null ? ctx.getOption(actionId, optionName).getAsChannel() : null;
		
		
		if (optRole != null) {
			return optRole;
		}
		
		System.out.println("No option found");
		
		// Fallback: get action data
		return ctx.getGuild().getTextChannelById(fallbackChannelId);
	}
	
	public static String resolveString(InteractionContext ctx, Action action, String optionName, String fallbackText) {
		System.out.println("[Resolver] Resolving " + optionName + " for action " + action.getId());
		System.out.println("[Resolver] DefaultValue = " + fallbackText);
		
		
		String actionId = action.getId();
		System.out.println("ACTION ID: " + actionId);
		// Check if options return anything (slash command may return smth, buttons always return null)
		String optString = ctx.getOption(actionId, optionName) != null ? ctx.getOption(actionId, optionName).getAsString() : null;
		
		System.out.println("[Resolver] Returned = " + optString);
		
		if (optString != null) {
			return optString;
		}
		
		System.out.println("No option found");
		
		// Fallback: get action data
		return fallbackText;
	}
}

