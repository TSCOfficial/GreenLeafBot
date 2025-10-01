package ch.greenleaf.features.commands;

import ch.greenleaf.features.ticketsystem.TicketManager;
import com.fasterxml.jackson.databind.JsonNode;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class TicketCommand implements ICommand {

    @Override
    public String getName() {
        return "ticketpanel";
    }

    @Override
    public String getDescription() {
        return "test";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of();
    }

    @Override
    public Map<String, List<?>> getAutocomplete() {
        return Map.of();
    }

    @Override
    public void execute(@NotNull SlashCommandInteractionEvent event) {
        new TicketManager().sendPanel(event);
        event.reply("Panel sent.").queue();
    }

    @Override
    public void execute(@NotNull JsonNode payload) {

    }
}
