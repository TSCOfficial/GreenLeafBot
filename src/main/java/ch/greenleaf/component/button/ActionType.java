package ch.greenleaf.component.button;

public enum ActionType {
    SEND_MESSAGE("send_message"),
    SEND_EMBED("send_embed"),
    ADD_ROLE("add_role"),
    REMOVE_ROLE("remove_role");

    private final String id;

    ActionType(String id) {
        this.id = id;
    }

    public String getId() { return id; }

    public static ActionType fromId(String id) {
        for (ActionType type : values()) {
            if (type.getId().equals(id)) return type;
        }
        return null; // or throw exception
    }
}

