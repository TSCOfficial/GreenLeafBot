package ch.greenleaf.interactions.actions;

import ch.greenleaf.DatabaseQuery;
import ch.greenleaf.Table;
import ch.greenleaf.interactions.InteractionContext;

import java.sql.ResultSet;

public class Action {
    private int id;
    private ActionType type;
    private int datasourceId;

	public Action() {
	
	}
	
    public Action(int id, String typeId, int datasourceId) {
        this.id = id;
        this.type = ActionType.getById(typeId);
        this.datasourceId = datasourceId;
        System.out.println("========");
        System.out.println(id);
        System.out.println(type.name());
        System.out.println(datasourceId);
    }

    public long getId() {
        return id;
    }

    public String getDatasourceTable() {
        return type.getTableName();
    }

    public int getDatasourceId() {
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
	public Action getById(int id) {
		try {
			ResultSet rs = new DatabaseQuery(Table.Action.SELF)
				.select()
				.where(Table.Action.ID, DatabaseQuery.Operator.EQUALS, id)
				.executeQuery();
			
			System.out.println(rs.getMetaData());
			
			rs.next();
			
			String type = rs.getString(Table.Action.TYPE);
			
			System.out.println(rs.getMetaData());
			
			this.id = id;
			this.type = ActionType.getById(type);
			this.datasourceId = rs.getInt((Table.Action.DATASOURCE_ID));
			
			return this;
			
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
