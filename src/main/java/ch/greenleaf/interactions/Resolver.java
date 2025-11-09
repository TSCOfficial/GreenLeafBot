package ch.greenleaf.interactions;

import ch.greenleaf.interactions.actions.ActionManager;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.Channel;

public class Resolver {
	
	public static Role resolveRole(InteractionContext ctx, ActionManager action, String optionName, long fallbackRoleId) {
		System.out.println(optionName);
		System.out.println(fallbackRoleId);
		
		// Check if options return anything (slash command mey return smth, buttons always return null)
		Role optRole = ctx.getOption(action.getId(), optionName) != null ? ctx.getOption(action.getId(), optionName).getAsRole() : null;
		
		
		if (optRole != null) {
			return optRole;
		}
		
		System.out.println("No option found");
		
		// Fallback: get action data
		return ctx.getGuild().getRoleById(fallbackRoleId);
	}
	
	public static Channel resolveChannel(InteractionContext ctx, ActionManager action, String optionName, long fallbackChannelId) {
		System.out.println("[RESOLVER] " + optionName);
		System.out.println("[RESOLVER] " + fallbackChannelId);
		
		// Check if options return anything (slash command mey return smth, buttons always return null)
		Channel optChannel = ctx.getOption(action.getId(), optionName) != null ? ctx.getOption(action.getId(), optionName).getAsChannel() : null;
		
		System.out.println("[RESOLVER] Returned = " + optChannel);
		
		if (optChannel != null) {
			return optChannel;
		}
		
		System.out.println("[RESOLVER] No option found");
		
		// Fallback: get action data
		return ctx.getGuild().getTextChannelById(fallbackChannelId);
	}
	
	public static String resolveString(InteractionContext ctx, ActionManager action, String optionName, String fallbackText) {
		System.out.println("[Resolver] Resolving " + optionName + " for action " + action.getId());
		System.out.println("[Resolver] DefaultValue = " + fallbackText);
		
		System.out.println("ACTION ID: " + action.getId());
		// Check if options return anything (slash command may return smth, buttons always return null)
		String optString = ctx.getOption(action.getId(), optionName) != null ? ctx.getOption(action.getId(), optionName).getAsString() : null;
		
		System.out.println("[Resolver] Returned = " + optString);
		
		if (optString != null) {
			return optString;
		}
		
		System.out.println("No option found");
		
		// Fallback: get action data
		return fallbackText;
	}
}

