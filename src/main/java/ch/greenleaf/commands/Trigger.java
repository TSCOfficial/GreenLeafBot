package ch.greenleaf.commands;

import ch.greenleaf.features.appinfo.AppInfo;
import ch.greenleaf.features.teamoverview.TeamOverview;
import com.fasterxml.jackson.databind.JsonNode;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class Trigger implements ISlashCommand{
	@Override
	public String getName() {
		return "trigger";
	}
	
	@Override
	public String getDescription() {
		return "FOR DEVELOPING TESTING!";
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
		new AppInfo(event.getGuild().getIdLong());
	}
	
	@Override
	public void execute(@NotNull JsonNode payload) {
	
	}
}
