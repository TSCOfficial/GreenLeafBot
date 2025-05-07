package ch.greenleaf;

public enum WSresponseStatus {
    MESSAGE_SENT("Message sent", "The Message has been sent");

    private final String name;
    private final String description;

    WSresponseStatus(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
