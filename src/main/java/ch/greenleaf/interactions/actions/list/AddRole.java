package ch.greenleaf.interactions.actions.list;

import ch.greenleaf.DatabaseQuery;
import ch.greenleaf.Table;
import ch.greenleaf.interactions.InteractionContext;
import ch.greenleaf.interactions.InteractionResponse;
import ch.greenleaf.interactions.actions.Action;
import ch.greenleaf.template.embed.Embed;
import ch.greenleaf.template.message.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.UserSnowflake;

import java.sql.ResultSet;

public class AddRole {

	// The action that contains the action data (action table and id)
    private final Action action;
	
	// The Interaction context (i.e. Button, Slash, ...)
    private final InteractionContext ctx;
	
	// The role object
	private Role role;

    /**
     * <h1>Message action</h1>
     * Send a custom message
     * @param action The connected action containing the required action database table and datasource id
     * @param ctx The interaction context, treating the actions differently if the interaction event comes from a Button, a Slashcommand or others
     */
    public AddRole(Action action, InteractionContext ctx) {
        this.action = action;
        this.ctx = ctx;
        fetchDatabase();
        execute();
    }

    /**
     * Get all needed data from the database
     */
    private void fetchDatabase() {
        try {
			ResultSet rs = new DatabaseQuery(action.getDatasourceTable())
				.where(Table.Role.ID, DatabaseQuery.Operator.EQUALS, action.getDatasourceId())
				.executeQuery();
			
            rs.next();
			
			System.out.println(action.getDatasourceTable());
			
			// Get values
			long role_id = rs.getLong(Table.Role.ROLE_ID);
			
			// Implement values in role object
			role = ctx.getGuild().getRoleById(role_id);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Add the role to the command author
     */
    private void execute() {
        ctx.getGuild().addRoleToMember((UserSnowflake) ctx.getAuthor(), role).queue();
    }
}

