package ch.greenleaf.template.message;

import ch.greenleaf.template.embed.Embed;

import java.util.ArrayList;
import java.util.List;

public class Message {
	
	private Integer id;
	private String text;
	private boolean isEphemeral = false;
	private List<Embed> embeds = new ArrayList<>();
	
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
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public List<Embed> getEmbeds() {
		return embeds;
	}
	
	public void setEmbeds(List<Embed> embeds) {
		this.embeds = embeds;
	}
	
	public void addEmbed(Embed embed) {
		this.embeds.add(embed);
	}
	
}
