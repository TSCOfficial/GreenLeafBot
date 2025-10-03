package ch.greenleaf.interactions;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.Channel;

public class Resolver {
	
	public static Role resolveRole(InteractionContext ctx, String optionName, long fallbackRoleId) {
		System.out.println(optionName);
		System.out.println(fallbackRoleId);
		// Check if options return anything (slash command mey return smth, buttons always return null)
		Role optRole = ctx.getOption(optionName) != null ? ctx.getOption(optionName).getAsRole() : null;
		
		
		if (optRole != null) {
			System.out.println(optRole.getName());
			return optRole;
		}
		
		System.out.println("No option found");
		
		// Fallback: get action data
		return ctx.getGuild().getRoleById(fallbackRoleId);
	}
	
	public static Channel resolveChannel(InteractionContext ctx, String optionName, long fallbackChannelId) {
		System.out.println(optionName);
		System.out.println(fallbackChannelId);
		// Check if options return anything (slash command mey return smth, buttons always return null)
		Channel optRole = ctx.getOption(optionName) != null ? ctx.getOption(optionName).getAsChannel() : null;
		
		
		if (optRole != null) {
			System.out.println(optRole.getName());
			return optRole;
		}
		
		System.out.println("No option found");
		
		// Fallback: get action data
		return ctx.getGuild().getTextChannelById(fallbackChannelId);
	}
}

