//package ch.greenleaf.components.embed;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import net.dv8tion.jda.api.EmbedBuilder;
//import net.dv8tion.jda.api.entities.MessageEmbed;
//
//import java.awt.*;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//public class EmbedManager
//{
//
//    public static MessageEmbed EmbedToMessageEmbed(ch.greenleaf.components.embed.Embed embed) {
//        EmbedBuilder builder = new EmbedBuilder();
//        builder.setTitle(embed.getTitle());
//        builder.setColor(embed.getColor());
//        builder.setDescription(embed.getDescription());
//        builder.setThumbnail(embed.getThumbnailUrl());
//        builder.setFooter(embed.getFooter(), embed.getFooterIcon());
//        builder.setTimestamp(embed.getTimestamp());
//        builder.setAuthor(embed.getAuthor(), embed.getAuthorUrl(), embed.getAuthorIcon());
//        builder.setImage(embed.getImageUrl());
//
//        embed.getFields().forEach(field -> {
//            if (field.title() != null && field.value() != null) {
//                builder.addField(
//                        field.title(),
//                        field.value(),
//                        field.inline()
//                );
//            } else {
//                builder.addBlankField(field.inline());
//            }
//        }
//        );
//        return builder.build();
//    }
//
//    /**
//     * Convert a json to a Embed.
//     *
//     * @param json
//     * @return
//     */
//    public static MessageEmbed JsonToEmbed(JsonNode json) {
//        JsonNode author = json.get("author");
//        String authorName = author != null && author.get("name") != null ? author.get("name").asText() : null;
//        String authorUrl = author != null && author.get("url") != null ? author.get("url").asText() : null;
//        String authorIcon = author != null && author.get("icon") != null ? author.get("icon").asText() : null;
//
//        String title = json.get("title") != null ? json.get("title").asText() : null;
//        String description = json.get("description") != null ? json.get("description").asText() : null;
//
//        JsonNode fields = json.get("fields");
//
//        String imageUrl = json.get("imageUrl") != null ? json.get("imageUrl").asText() : null;
//        String thumbnailUrl = json.get("thumbnailUrl") != null ? json.get("thumbnailUrl").asText() : null;
//
//        JsonNode footer = json.get("footer");
//        String footerText = footer != null && footer.get("text") != null ? footer.get("text").asText() : null;
//        String footerIcon = footer != null && footer.get("icon") != null ? footer.get("icon").asText() : null;
//
//        String timestampData = json.get("timestamp") != null ? json.get("timestamp").asText() : null;
//        LocalDateTime timestamp = timestampData != null ? LocalDateTime.parse(timestampData) : null;
//
//        JsonNode colorCode = json.get("color");
//        Color color = null;
//        int red;
//        int green;
//        int blue ;
//        if (colorCode != null) {
//            red = colorCode.get("r") != null ? colorCode.get("r").asInt() : 0;
//            green = colorCode.get("g") != null ? colorCode.get("g").asInt() : 0;
//            blue = colorCode.get("b") != null ? colorCode.get("b").asInt() : 0;
//            color = new Color(red, green, blue);
//        }
//
//
//
//
//        Embed embed = new Embed();
//        embed.setTitle(title);
//        embed.setDescription(description);
//        embed.setAuthor(authorName);
//        embed.setAuthorUrl(authorUrl);
//        embed.setAuthorIcon(authorIcon);
//        embed.setImageUrl(imageUrl);
//        embed.setThumbnailUrl(thumbnailUrl);
//        embed.setFooter(footerText);
//        embed.setFooterIcon(footerIcon);
//        embed.setTimestamp(timestamp);
//        embed.setColor(color);
//
//        List<Field> embedFields = new ArrayList<>();
//        if (fields != null && !fields.isEmpty()) {
//            for (JsonNode field : fields) {
//                Field embedField = new Field(
//                        field.get("title").asText(),
//                        field.get("value").asText(),
//                        field.get("inline").asBoolean()
//                );
//                embedFields.add(embedField);
//            }
//            embed.setFields(embedFields);
//        }
//
//
//        return EmbedToMessageEmbed(embed);
//    }
//}
