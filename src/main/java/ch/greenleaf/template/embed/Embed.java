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

	// Intern embed id
	private int id;
	
	// Embed title
    private String title;
	
	// Embed description
    private String description;
	
	// Embed fields
    private List<Field> fields = new ArrayList<>();
	
	// Embed author name
    private String author;
	
	// Embed author URL
    private String authorUrl;
	
	// Embed author icon
    private String authorIcon;
	
	// Embed image URL
    private String imageUrl;
	
	// Embed thumbnail URL
    private String thumbnailUrl;
	
	// Embed footer text
    private String footer;
	
	// Embed footer icon
    private String footerIcon;
	
	// Embed footer timestamp
    private LocalDateTime timestamp;
	
	// Embed color
    private Color color;
	
	/**
	 * Get the embed author name
	 * @return Author name
	 */
    public String getAuthor() {
        return author;
    }
	
	/**
	 * Set the embed author name
	 * @param author Author name
	 */
    public void setAuthor(String author) {
        this.author = author;
    }
	
	/**
	 * Get the author URL
	 * @return Author URL
	 */
    public String getAuthorUrl() {
        return authorUrl;
    }
	
	/**
	 * Set the author URL
	 * @param authorUrl Author URL
	 */
    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }
	
	/**
	 * Get the author icon
	 * @return Author icon
	 */
    public String getAuthorIcon() {
        return authorIcon;
    }
	
	/**
	 * Set the author icon
	 * @param authorIcon Author icon
	 */
    public void setAuthorIcon(String authorIcon) {
        this.authorIcon = authorIcon;
    }
	
	/**
	 * Get the description text
	 * @return Description text
	 */
    public String getDescription() {
        return description;
    }
	
	/**
	 * Set the description text
	 * @param description Description text
	 */
    public void setDescription(String description) {
        this.description = description;
    }
	
	/**
	 * Get all embed fields
	 * @return All fields
	 */
    public List<Field> getFields() {
        return fields;
    }
	
	/**
	 * Set embed fields
	 * @param fields Fields
	 */
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
	
	/**
	 * Get footer text
	 * @return Footer text
	 */
    public String getFooter() {
        return footer;
    }
	
	/**
	 * Set footer text
	 * @param footer Footer text
	 */
    public void setFooter(String footer) {
        this.footer = footer;
    }
	
	/**
	 * Get footer icon
	 * @return footer icon
	 */
    public String getFooterIcon() {
        return footerIcon;
    }
	
	/**
	 * Set footer icon
	 * @param footerIcon Footer icon
	 */
    public void setFooterIcon(String footerIcon) {
        this.footerIcon = footerIcon;
    }
	
	/**
	 * Get image URL
	 * @return Image URL
	 */
    public String getImageUrl() {
        return imageUrl;
    }
	
	/**
	 * Set image URL
	 * @param imageUrl Image URL
	 */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
	
	/**
	 * Get thumbnail URL
	 * @return Thumbnail URL
	 */
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
	
	/**
	 * Set thumbnail URL
	 * @param thumbnailUrl Thumbnail URL
	 */
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
	
	/**
	 * Get timestamp
	 * @return Timestamp
	 */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
	
	/**
	 * Set timestamp
	 * @param timestamp Timestamp
	 */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
	
	/**
	 * Get title
	 * @return Title
	 */
    public String getTitle() {
        return title;
    }
	
	/**
	 * Set title
	 * @param title Title
	 */
    public void setTitle(String title) {
        this.title = title;
    }
	
	/**
	 * Get color
	 * @return Color
	 */
    public Color getColor() {
        return color;
    }
	
	/**
	 * Set color
	 * @param color Color
	 */
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
