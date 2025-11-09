package ch.greenleaf.interactions.actions;

import ch.greenleaf.DatabaseQuery;
import ch.greenleaf.Table;
import ch.greenleaf.interactions.InteractionContext;

import java.sql.ResultSet;

/**
 * Manages the actions, so that the correct action-data are fetched and given to the appropriate action
 */
public class ActionManager {
    private String id;
    private ActionType type;
    private String datasourceId;

    public String getId() {
        return id;
    }

    public String getDatasourceTable() {
        return type.getTableName();
    }

    public String getDatasourceId() {
        return datasourceId;
    }
	
	/**
	 * Execute the correct action by passing the action data and the event
	 * @param ctx Interaction context (event)
	 */
    public void execute(InteractionContext ctx) {
        System.out.println("executing action: " + type.name());
        ActionRegistry.getByType(type).execute(this, ctx);
    }
	
	/**
	 * Get the action data from the database
	 * @param id The action id
	 * @return The action containing all needed data
	 */
	public ActionManager getById(String id) {
		try {
			ResultSet rs = new DatabaseQuery(Table.Action.SELF)
				.select()
//				.join(
//					Table.ActionType.SELF,
//					Table.Action.TYPE_ID, DatabaseQuery.Operator.EQUALS, Table.define(Table.ActionType.SELF, Table.ActionType.ID)
//				)
				.where(Table.Action.ID, DatabaseQuery.Operator.EQUALS, id)
				.executeQuery();
			
			System.out.println(rs.getMetaData());
			
			rs.next();
			
			String typeId = rs.getString(Table.Action.TYPE_ID);
			
			System.out.println(rs.getMetaData());
			
			this.id = id;
			this.type = ActionType.getById(typeId);
			this.datasourceId = rs.getString((Table.Action.DATASOURCE_ID));
			
			return this;
			
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
