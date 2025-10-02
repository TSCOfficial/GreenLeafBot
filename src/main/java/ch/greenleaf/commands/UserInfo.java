package ch.greenleaf.commands;

import ch.greenleaf.common.time.TimestampType;
import ch.greenleaf.common.time.Timestamp;
import com.fasterxml.jackson.databind.JsonNode;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserInfo implements ICommand {


    @Override
    public String getName() {
        return "userinfo";
    }

    @Override
    public String getDescription() {
        return "Get a users information.";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.USER, "user", "The user you want to get the informations from."));
        return data;
    }

    @Override
    public Map<String, List<?>> getAutocomplete() {
        return Map.of();
    }

    @Override
    public void execute(@NotNull SlashCommandInteractionEvent event) {
        Member member;
        if (event.getOptions().contains(event.getOption("user"))){
            member = event.getOption("user").getAsMember();
        } else{
            member = event.getMember();
        }

        EmbedBuilder embedUserInfo = new EmbedBuilder();

        embedUserInfo.setTitle(member.getEffectiveName() + "'s infos");

        embedUserInfo.setColor(member.getColor());

        // Global
        List<String> globalUserInfos = new ArrayList<>();

        globalUserInfos.add("**User**: @" + member.getUser().getName());
        globalUserInfos.add("**ID**: `" + member.getUser().getId() + "`");

        long accountCreatedEpochtime = member.getUser().getTimeCreated().toEpochSecond();
        globalUserInfos.add(
                "**Created**: " + Timestamp.convert(accountCreatedEpochtime, TimestampType.LONGDATE)
                        + "(" + Timestamp.convert(accountCreatedEpochtime, TimestampType.RELATIVE) + ")"
                           );
        globalUserInfos.add("[**Avatar**](" + member.getUser().getAvatarUrl() + ")");

        embedUserInfo.addField(
                "Global informations",
                String.join("\n", globalUserInfos),
                false
                              );

//        embedUserInfo.addField(
//                "Joined Guild",
//                member.getTimeJoined()
//                      .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
//                true);
//
//        embedUserInfo.addField(
//                "Joined Discord",
//                member.getUser().getTimeCreated()
//                      .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
//                true
//                );

        embedUserInfo.addField(
                "Top role",
                member.getRoles().getFirst().getAsMention(),
                true
                              );

        embedUserInfo.setFooter(event.getMember().getEffectiveName(), event.getMember().getEffectiveAvatarUrl());

        embedUserInfo.setThumbnail(member.getEffectiveAvatarUrl());

        //event.getChannel().sendMessage("message").setEmbeds(embedUserInfo.build()).queue();
        event.reply("").setEmbeds(embedUserInfo.build()).queue();
    }

    @Override
    public void execute(@NotNull JsonNode payload) {

    }
}
