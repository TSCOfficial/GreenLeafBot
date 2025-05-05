package ch.greenleaf.component.button.data.actions;

import ch.greenleaf.component.button.data.ActionData;

public class DataActionSendMessage extends ActionData {

    private String message;
    private String channelId;
    private String categoryId;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
