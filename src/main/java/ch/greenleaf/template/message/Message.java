package ch.greenleaf.template.message;

import ch.greenleaf.template.embed.Embed;

import java.util.ArrayList;
import java.util.List;

public class Message {

    private String id;
    private String guildId;
    private String text;
    private boolean isEphemeral = false;
    private String channelId;
	private List<Embed> embeds = new ArrayList<>();

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public boolean isEphemeral() {
        return isEphemeral;
    }

    public void setEphemeral(boolean ephemeral) {
        isEphemeral = ephemeral;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public List<Embed> getEmbeds() {
		return embeds;
	}
	
	public void setEmbeds(List<Embed> embeds) {
		this.embeds = embeds;
	}
}
