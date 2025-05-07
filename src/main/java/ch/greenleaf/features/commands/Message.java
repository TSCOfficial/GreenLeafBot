package ch.greenleaf.features.commands;

import ch.greenleaf.BackendDispatcher;
import ch.greenleaf.Client;
import ch.greenleaf.ICommand;
import ch.greenleaf.WSresponseStatus;
import ch.greenleaf.template.embed.Embed;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
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

        try {
            String message = payload.get("message").asText();
            boolean hasEmbed = payload.get("embeds") != null;

            List<MessageEmbed> embeds = new ArrayList<>();

            if (hasEmbed) {
                payload.get("embeds").forEach(embedData -> {
                    Embed embed = new Embed();
                    embed.setTitle(embedData.get("title").asText());
                    embed.setDescription(embedData.get("description").asText());
                    EmbedBuilder convertedEmbed = embed.convert(embed);
                    embeds.add(convertedEmbed.build());
                });
            }

            net.dv8tion.jda.api.entities.Message sentMessage = null;

            if (hasEmbed) {
                sentMessage = Client.client.getShardManager().getGuildById("1228461292440780801").getTextChannelById("1231933541017845882").sendMessage(message).setEmbeds(embeds).complete();
            } else {
                sentMessage = Client.client.getShardManager().getGuildById("1228461292440780801").getTextChannelById("1231933541017845882").sendMessage(message).complete();
            }



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
