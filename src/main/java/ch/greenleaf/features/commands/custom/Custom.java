package ch.greenleaf.features.commands.custom;

import ch.greenleaf.features.commands.ICommand;
import com.fasterxml.jackson.databind.JsonNode;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class Custom implements ICommand {
	
	private String name;
	private String description;

	@Override
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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
	
	}
	
	@Override
	public void execute(@NotNull JsonNode payload) {
	
	}
}
