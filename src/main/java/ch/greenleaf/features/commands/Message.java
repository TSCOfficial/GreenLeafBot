package ch.greenleaf.features.commands;

import ch.greenleaf.BackendDispatcher;
import ch.greenleaf.Client;
import ch.greenleaf.ICommand;
import ch.greenleaf.WSresponseStatus;
import ch.greenleaf.template.embed.EmbedManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.jetbrains.annotations.NotNull;

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
        event.reply("You want to send \"" + event.getOption("text").getAsString() + "\".").queue();
    }

    @Override
    public void execute(@NotNull JsonNode payload) {
        ObjectMapper mapper = new ObjectMapper();

        MessageCreateBuilder builder = new MessageCreateBuilder();

        try {
            String message = payload.get("message").asText();
            boolean hasEmbeds = payload.get("embeds") != null;

            // Message
            if (!message.isEmpty()) {
                builder.setContent(message);
            }

            // Embeds
            List<MessageEmbed> embeds = new ArrayList<>();

            if (hasEmbeds) {
                payload.get("embeds").forEach(embedData -> {
                    MessageEmbed embed = EmbedManager.JsonToEmbed(embedData);
                    embeds.add(embed);
                });
                builder.setEmbeds(embeds);
            }

            // Handle sending message
            net.dv8tion.jda.api.entities.Message sentMessage = Client.client.getShardManager().getGuildById("1228461292440780801").getTextChannelById("1231933541017845882").sendMessage(builder.build()).complete();

            // Erfolg zurücksenden
            JsonNode response = mapper.createObjectNode()
                    .put("messageId", sentMessage.getId())
                    .put("info", WSresponseStatus.MESSAGE_SENT.getDescription());
            BackendDispatcher.responseSuccess(response);

        }
        catch (Exception e) {
            JsonNode response = mapper.createObjectNode()
                    .put("code", "SEND_FAILED")
                    .put("message", e.getMessage());
            BackendDispatcher.responseError(response);
        }
    }
}
