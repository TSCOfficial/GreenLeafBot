package ch.greenleaf.commands.custom;

import ch.greenleaf.commands.ISlashCommand;
import com.fasterxml.jackson.databind.JsonNode;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A custom slash command object<br>
 * To use the command option, the
 */
public class SlashCommand implements ISlashCommand {
	
	// Command name
	private String name;
	
	// Command description
	private String description;
	
	// Command options
	private List<OptionData> options = new ArrayList<>();
	
	// Option autocompletion
	private Map<String, List<?>> autocomplete = new HashMap<>();
	
	/**
	 * Get command name
	 * @return Command name
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * Set command name
	 * @param name Command name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get command description
	 * @return Command description
	 */
	@Override
	public String getDescription() {
		return description;
	}
	
	/**
	 * Set command description
	 * @param description Description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Get command options
	 * @return The command options
	 */
	@Override
	public List<OptionData> getOptions() {
		return options;
	}
	
	/**
	 * Set command options
	 * @param options Command options
	 */
	public void setOptions(List<OptionData> options) {
		this.options = options;
	}
	
	/**
	 * Add a command option
	 * @param option Command option
	 */
	public void addOption(OptionData option) {
		this.options.add(option);
	}
	
	/**
	 * Get autocomplete attributes
	 * @return Autocomplete map of option name and its autocomplete values
	 */
	@Override
	public Map<String, List<?>> getAutocomplete() {
		return autocomplete;
	}
	
	/**
	 * Set autocomplete attributes
	 * @param autocomplete Autocomplete map of options and values
	 */
	public void setAutocomplete(Map<String, List<?>> autocomplete) {
		this.autocomplete = autocomplete;
	}
	
	/**
	 * Add autocompletion for an option
	 * @param key Option name
	 * @param autocomplete Autocomplete values
	 */
	public void addAutocomplete(String key, List<?> autocomplete) {
		this.autocomplete.put(key, autocomplete);
	}
	
	@Override
	public void execute(@NotNull SlashCommandInteractionEvent event) {
		/* Nothing to execute here
		The Slash command gets its data from the database and executes its attributed actions in the CustomCommands class
		 */
	}
	
	@Override
	public void execute(@NotNull JsonNode payload) {
		/* Nothing to execute here
		The Slash command gets its data from the database and executes its attributed actions in the CustomCommands class
		 */
	}
}
