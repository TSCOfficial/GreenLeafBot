package ch.greenleaf.interaction.actions;

import ch.greenleaf.interaction.InteractionContext;

public class Action {
    private final int id;
    private final ActionType type;
    private final int datasourceId;

    public Action(int id, int typeId, int datasourceId) {
        this.id = id;
        this.type = ActionType.fromTypeId(typeId);
        this.datasourceId = datasourceId;
    }

    public int getId() {
        return id;
    }

    public String getDatasourceTable() {
        return type.getTableName();
    }

    public int getDatasourceId() {
        return datasourceId;
    }

    public void execute(InteractionContext ctx) {
        ActionRegistry.get(type).execute(ctx);
    }
}
