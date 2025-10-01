package ch.greenleaf.template.message;

import ch.greenleaf.template.embed.Embed;

import java.util.ArrayList;
import java.util.List;

/**
 * Create a Message that can be sent to Discord<br>
 * <i>[!] Any embed need to be made Discord-compatible by using {@link Embed#build()}, before sending.</i>
 */
public class Message {
	
	// Intern message ID
	private Integer id;
	
	// The channel ID to send the message into
	private long channelId;
	
	// The messages text
	private String text;
	
	// Whether the message is ephemeral or not
	private boolean isEphemeral = false;
	
	// The messages embeds
	private List<Embed> embeds = new ArrayList<>();
	
	/**
	 * Get if the message is ephemeral or not
	 * @return The ephemeral state
	 */
	public boolean isEphemeral() {
		return isEphemeral;
	}
	
	/**
	 * Set the messages ephemeral state
	 * @param ephemeral The state
	 */
	public void setEphemeral(boolean ephemeral) {
		isEphemeral = ephemeral;
	}
	
	/**
	 * Get the messages text
	 * @return The text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Set the messages text
	 * @param text The text
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Get the intern message id
	 * @return The intern id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Set the intern message id
	 * @param id The intern id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * Get all embeds<br>
	 * <i>[!] Any embed need to be made Discord-compatible by using {@link Embed#build()}, before sending.</i>
	 * @return All embeds
	 */
	public List<Embed> getEmbeds() {
		return embeds;
	}
	
	/**
	 * Set the embeds<br>
	 * <i>[!] Any embed need to be made Discord-compatible by using {@link Embed#build()}, before sending.</i>
	 * @param embeds The embeds to set
	 */
	public void setEmbeds(List<Embed> embeds) {
		this.embeds = embeds;
	}
	
	/**
	 * Add an embed<br>
	 * <i>[!] Any embed need to be made Discord-compatible by using {@link Embed#build()}, before sending.</i>
	 * @param embed The embed to append
	 */
	public void addEmbed(Embed embed) {
		this.embeds.add(embed);
	}
	
	/**
	 * Get channel ID
	 * @return Channel ID
	 */
	public long getChannelId() {
		return channelId;
	}
	
	/**
	 * Set channel ID
	 * @param channelId Channel ID
	 */
	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}
}
