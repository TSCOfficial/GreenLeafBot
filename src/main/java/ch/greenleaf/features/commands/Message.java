package ch.greenleaf.features.commands;

import ch.greenleaf.BackendDispatcher;
import ch.greenleaf.Client;
import ch.greenleaf.ICommand;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
            System.out.println(payload);
            String message = payload.get("message").asText();
            System.out.println(message);
            net.dv8tion.jda.api.entities.Message sentMessage = Client.client.getShardManager().getGuildById("1228461292440780801").getTextChannelById("1231933541017845882").sendMessage(message).complete();
            System.out.println(2);


            // Erfolg zurücksenden
            JsonNode response = mapper.createObjectNode()
                    .put("type", "response")
                    .put("status", "success")
                    .set("payload", mapper.createObjectNode()
                            .put("messageId", sentMessage.getId())
                            .put("info", "Message sent"));

            BackendDispatcher.sendMessage(response.toString());
        }
        catch (Exception e) {
            System.out.println(e);

            // Erfolg zurücksenden
            JsonNode response = mapper.createObjectNode()
                    .put("type", "response")
                    .put("status", "error")
                    .set("payload", mapper.createObjectNode()
                            .put("code", "SEND_FAILED")
                            .put("message", e.getMessage()));

            BackendDispatcher.sendMessage(response.toString());
        }
    }
}
