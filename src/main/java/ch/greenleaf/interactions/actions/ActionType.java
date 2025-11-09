package ch.greenleaf.interactions.actions;

import ch.greenleaf.Table;
import ch.greenleaf.interactions.actions.list.AddRole;
import ch.greenleaf.interactions.actions.list.SendMessage;

import java.util.Objects;

/**
 * Manage available actions using their id and their table-names
 */
public enum ActionType {
	// Message actions
    SEND_MESSAGE(SendMessage.ID, Table.Message.SELF),
	
	// Role actions
    ADD_ROLE(AddRole.ID, Table.Role.SELF);

    private final String id;
    private final String tableName;

    ActionType(String id, String tableName) {
        this.id = id;
        this.tableName = tableName;
    }

    public String getId() {
        return id;
    }

    public String getTableName() {
        return tableName;
    }

    public static ActionType getById(String id) {
        for (ActionType type : values()) {
            if (Objects.equals(type.id, id)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown action type: " + id);
    }
}

