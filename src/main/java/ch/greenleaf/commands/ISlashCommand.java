package ch.greenleaf.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.Map;

public interface ISlashCommand {

    /**
     * Get the name of a slashcommand.
     *
     * @return a {@link String}
     */
    String getName();


    /**
     * Get the description of a slashcommand.
     *
     * @return a {@link String}
     */
    String getDescription();

    /**
     * Get the options and choices of a slashcommand.
     *
     * @return a {@link List} of {@link OptionData}.
     */
    List<OptionData> getOptions();

    /**
     * Get the autocompletion of a slashcommand.
     * @return a {@link Map} of a {@link String} & a {@link List}.
     */
    Map<String, List<?>> getAutocomplete();

    /**
     * Execute a slashcommand by its Interaction.<br>
     * Write what should happen in the @Overwrite Method.
     * @param event The {@link SlashCommandInteractionEvent}
     */
    void execute(@NotNull SlashCommandInteractionEvent event);

    /**
     * Execute a slashcommand from the backend.<br>
     * Write what should happen in the @Overwrite Method.
     * @param payload Backend data
     */
    void execute(@NotNull JsonNode payload);
}
