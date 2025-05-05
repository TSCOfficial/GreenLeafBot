package ch.greenleaf;

public enum Limit {

    BUTTON_ACTIONS("button actions", 5),
    SIMULTANEOUS_OPEN_TICKETS("simultaneous open tickets", 1);

    private final String name;
    private final int limit;


    Limit(String name, int limit) {
        this.name = name;
        this.limit = limit;
    }

    public String getName() {
        return name;
    }

    public int getLimit() {
        return limit;
    }
}
