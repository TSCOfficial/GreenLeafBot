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
        System.out.println("========");
        System.out.println(id);
        System.out.println(type.name());
        System.out.println(datasourceId);
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
        System.out.println("executing action: " + type.name());
        ActionRegistry.get(type).execute(this, ctx);
    }
}
