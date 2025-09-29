package ch.greenleaf.template.embed;

import ch.greenleaf.Table;
import ch.greenleaf.template.message.Message;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Embed extends Message {

    private String title;
    private String description;
    private List<Field> fields = new ArrayList<>();
    private String author;
    private String authorUrl;
    private String authorIcon;
    private String imageUrl;
    private String thumbnailUrl;
    private String footer;
    private String footerIcon;
    private LocalDateTime timestamp;
    private Color color;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public String getAuthorIcon() {
        return authorIcon;
    }

    public void setAuthorIcon(String authorIcon) {
        this.authorIcon = authorIcon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public void addField(Field field) {
        this.fields.add(field);
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getFooterIcon() {
        return footerIcon;
    }

    public void setFooterIcon(String footerIcon) {
        this.footerIcon = footerIcon;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
	
	/**
	 * Build the discord-compatible embed
	 * @return The {@link MessageEmbed}
	 */
    public MessageEmbed build() {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle(title);
		builder.setColor(color);
		builder.setDescription(description);
		builder.setThumbnail(thumbnailUrl);
		builder.setFooter(footer, footerIcon);
		builder.setTimestamp(timestamp);
		builder.setAuthor(author, authorUrl, authorUrl);
		builder.setImage(imageUrl);
		
		fields.forEach(field -> {
				if (field.title() != null && field.value() != null) {
					builder.addField(
						field.title(),
						field.value(),
						field.inline()
					);
				} else {
					builder.addBlankField(field.inline());
				}
			}
		);
		return builder.build();
    }
}
