package ch.greenleaf.interactions;

import net.dv8tion.jda.api.entities.Role;

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
}

