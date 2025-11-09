package ch.greenleaf.interactions.actions.list;

import ch.greenleaf.DatabaseQuery;
import ch.greenleaf.Table;
import ch.greenleaf.interactions.InteractionContext;
import ch.greenleaf.interactions.Resolver;
import ch.greenleaf.interactions.actions.ActionManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.UserSnowflake;

import java.sql.ResultSet;

/**
 * Add a role
 */
public class AddRole extends Action{
	
	public enum Variable {
		ROLE_ID,
		USER_ID
	}
	
	public static final String ID = "/role/add";
	
	// The role
	private Role role;
	
	// The member to add the role to
	private Member member;

    /**
     * <h1>Message action</h1>
     * Send a custom message
     * @param actionManager The connected action containing the required action database table and datasource id
     * @param ctx The interaction context, treating the actions differently if the interaction event comes from a Button, a Slashcommand or others
     */
    public AddRole(ActionManager actionManager, InteractionContext ctx) {
        super(actionManager, ctx);
    }

    /**
     * Get all needed data from the database
     */
	@Override
    protected void fetchDatabase() {
        try {
			ResultSet rs = new DatabaseQuery(actionManager.getDatasourceTable())
				.where(Table.Role.ID, DatabaseQuery.Operator.EQUALS, actionManager.getDatasourceId())
				.executeQuery();
			
            rs.next();
			
			System.out.println(actionManager.getDatasourceTable());
			
			// Get values
			long role_id = rs.getLong(Table.Role.ROLE_ID);
			
			role = Resolver.resolveRole(ctx, actionManager, Variable.ROLE_ID.name(), role_id);
			System.out.println(role.getName());
			
			member = ctx.getAuthor(); // Default: Interaction author

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Add the role to the command author
     */
	@Override
    public void execute() {
        ctx.getGuild().addRoleToMember(member, role).queue();
    }
}

