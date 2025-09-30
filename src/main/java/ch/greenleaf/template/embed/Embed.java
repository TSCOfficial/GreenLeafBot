package ch.greenleaf.template.embed;

import ch.greenleaf.DatabaseQuery;
import ch.greenleaf.Table;
import ch.greenleaf.template.message.Message;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Embed extends Message {

	private int id;
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
	
	/**
	 * Add a field using the {@link Field}
	 * @param field The {@link Field}, containing all field attributes
	 */
    public void addField(Field field) {
        this.fields.add(field);
    }
	
	/**
	 * Add a field using the direct attributes
	 * @param title The fields title
	 * @param value The fields value
	 * @param isInline Whether the field is inline or not
	 */
	private void addField(@Nullable String title, @Nullable String value, boolean isInline) {
		Field field = new Field(title, value, isInline);
		this.fields.add(field);
	}
	
	/**
	 * Add a blank field
	 * @param isInline Whether the field is inline or not
	 */
	private void addBlankField(boolean isInline) {
		Field field = new Field(null, null, isInline);
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
						field.isInline()
					);
				} else {
					builder.addBlankField(field.isInline());
				}
			}
		);
		return builder.build();
    }
	
	/**
	 * Get all attributes from the database using the ID
	 * @param id The embed id
	 * @return The built embed
	 */
	public Embed getById(int id) {
		try {
			ResultSet rs = new DatabaseQuery(Table.Embed.SELF)
				.select()
				.where(Table.Embed.ID, DatabaseQuery.Operator.EQUALS, id)
				.executeQuery();
			
			rs.next();
			
			this.id = id;
			this.title = rs.getString(Table.Embed.TITLE);
			this.description = rs.getString((Table.Embed.DESCRIPTION));
			this.author = rs.getString(Table.Embed.AUTHOR);
			this.footer = rs.getString(Table.Embed.FOOTER);
			
			return this;

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
