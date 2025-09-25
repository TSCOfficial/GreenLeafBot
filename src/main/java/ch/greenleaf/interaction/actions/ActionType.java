package ch.greenleaf.interaction.actions;

public enum ActionType {
    SEND_MESSAGE(101, "message"),
    EDIT_MESSAGE(102, "message"),
    REMOVE_ROLE(201, "role"),
    ASSIGN_ROLE(202, "role"),
    OPEN_URL(000, null);

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

    public static ActionType fromTypeId(int id) {
        for (ActionType t : values()) {
            if (t.id == id) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown action type: " + id);
    }
}

