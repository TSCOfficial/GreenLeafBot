package ch.greenleaf.features.commands;

import ch.greenleaf.*;
import ch.greenleaf.template.embed.EmbedManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Message implements ICommand{

    @Override
    public String getName() {
        return "message";
    }

    @Override
    public String getDescription() {
        return "Prints a Hello World with your name in the chat.";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.STRING, "text", "The message.", true, false));
        return data;
    }

    @Override
    public Map<String, List<?>> getAutocomplete() {
        return Map.of();
    }

    @Override
    public void execute(@NotNull SlashCommandInteractionEvent event) {
        try {
            Connection conn = Database.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from button");
            rs.next();
            System.out.println(rs.getString("label"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        event.reply("You want to send \"" + event.getOption("text").getAsString() + "\".").queue();
    }

    @Override
    public void execute(@NotNull JsonNode payload) {
        System.out.println("Sent in discord: " + payload);
        ObjectMapper mapper = new ObjectMapper();

        MessageCreateBuilder builder = new MessageCreateBuilder();

        try {
            JsonNode json = mapper.readTree(payload.asText());
            String message = json.get("message").asText();
            System.out.println(message);
            boolean hasEmbeds = json.get("embeds") != null;

            // Message
            if (!message.isEmpty()) {
                builder.setContent(message);
            }

            // Embeds
            List<MessageEmbed> embeds = new ArrayList<>();

            if (hasEmbeds) {
                json.get("embeds").forEach(embedData -> {
                    MessageEmbed embed = EmbedManager.JsonToEmbed(embedData);
                    embeds.add(embed);
                });
                builder.setEmbeds(embeds);
            }

            System.out.println("Sending message now");
            // Handle sending message
            net.dv8tion.jda.api.entities.Message sentMessage = Client.client.getShardManager().getGuildById("1228461292440780801").getTextChannelById("1231933541017845882").sendMessage(builder.build()).complete();
            System.out.println("Message sent");
            // Erfolg zurücksenden
            JsonNode response = mapper.createObjectNode()
                    .put("messageId", sentMessage.getId())
                    .put("info", WSresponseStatus.MESSAGE_SENT.getDescription());
            BackendDispatcher.responseSuccess(response);

        }
        catch (Exception e) {
            System.out.println(e);
            JsonNode response = mapper.createObjectNode()
                    .put("code", "SEND_FAILED")
                    .put("message", e.getMessage());
            BackendDispatcher.responseError(response);
        }
    }
}
