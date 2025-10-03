package ch.greenleaf.interactions.actions;

import ch.greenleaf.DatabaseQuery;
import ch.greenleaf.Table;
import ch.greenleaf.interactions.InteractionContext;

import java.sql.ResultSet;

public class Action {
    private String id;
    private ActionType type;
    private String datasourceId;

	public Action() {
	
	}
	
    public Action(String id, String typeId, String datasourceId) {
        this.id = id;
        this.type = ActionType.getById(typeId);
        this.datasourceId = datasourceId;
        System.out.println("========");
        System.out.println(id);
        System.out.println(type.name());
        System.out.println(datasourceId);
    }

    public String getId() {
        return id;
    }

    public String getDatasourceTable() {
        return type.getTableName();
    }

    public String getDatasourceId() {
        return datasourceId;
    }

    public void execute(InteractionContext ctx) {
        System.out.println("executing action: " + type.name());
        ActionRegistry.getByType(type).execute(this, ctx);
    }
	
	/**
	 * Build a message embed by getting all attributes from the database using only the ID
	 * @param id The embed id
	 * @return The built embed
	 */
	public Action getById(String id) {
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
