package ch.greenleaf.template.embed;

import net.dv8tion.jda.api.EmbedBuilder;

public class EmbedManager
{

    public static EmbedBuilder convertToBuilder(ch.greenleaf.template.embed.Embed embed) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(embed.getTitle());
        builder.setColor(embed.getColor());
        builder.setDescription(embed.getDescription());
        builder.setThumbnail(embed.getThumbnailUrl());
        builder.setFooter(embed.getFooter(), embed.getFooterIcon());
        builder.setTimestamp(embed.getTimestamp());
        builder.setAuthor(embed.getAuthor(), embed.getAuthorIcon());
        builder.setImage(embed.getImageUrl());

        embed.getFields().forEach(field -> {
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
        return builder;
    }
}
