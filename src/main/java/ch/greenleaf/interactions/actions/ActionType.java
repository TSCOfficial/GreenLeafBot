package ch.greenleaf.interactions.actions;

import ch.greenleaf.Table;

/**
 * Manage available actions, their id and their table-names
 */
public enum ActionType {
	// Messages, ID 100+
    SEND_MESSAGE(101, Table.Message.SELF),
	
	// Roles, ID 200+
    ADD_ROLE(201, Table.Role.SELF);

    private final int id;
    private final String tableName;

    ActionType(int id, String tableName) {
        this.id = id;
        this.tableName = tableName;
    }

    public int getId() {
        return id;
    }

    public String getTableName() {
        return tableName;
    }

    public static ActionType getById(int id) {
        for (ActionType t : values()) {
            if (t.id == id) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown action type: " + id);
    }
}

