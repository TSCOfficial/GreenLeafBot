package ch.greenleaf.features.commands;

import ch.greenleaf.ICommand;
import com.fasterxml.jackson.databind.JsonNode;
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

    }
}
