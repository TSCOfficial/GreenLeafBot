package ch.greenleaf.features.commands;

import com.fasterxml.jackson.databind.JsonNode;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class AddRole implements ICommand {
	@Override
	public String getName() {
		return "addrole";
	}
	
	@Override
	public String getDescription() {
		return "Füge eine Rolle hinzu";
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
