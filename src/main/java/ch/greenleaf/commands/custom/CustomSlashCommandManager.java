package ch.greenleaf.commands.custom;

import ch.greenleaf.commands.SlashCommandManager;
import ch.greenleaf.DatabaseQuery;
import ch.greenleaf.Table;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomSlashCommandManager {
	
	public static void load() {
		loadSlashCommand();
//		try {
//			ResultSet rs = new DatabaseQuery(Table.Command.SELF)
//				.join( // Select any options a command has, if none, only show command without options
//					DatabaseQuery.JoinType.LEFT, // <- Left: even without options a command should be shown
//					Table.CommandOption.SELF,
//					Table.define(Table.Command.SELF, Table.Command.ID), DatabaseQuery.Operator.EQUALS, Table.CommandOption.COMMAND_ID
//				)
//				.select()
//				.executeQuery();
//
//			while (rs.next()) {
//				// fetch slash command data
//				String name = rs.getString(Table.define(Table.Command.SELF, Table.Command.NAME));
//				String description = rs.getString(Table.Command.DESCRIPTION);
//				String optionName = rs.getString(Table.define(Table.CommandOption.SELF, Table.CommandOption.NAME));
//				String optionDescription = rs.getString(Table.define(Table.CommandOption.SELF, Table.CommandOption.DESCRIPTION));
//				boolean optionIsRequired = rs.getBoolean(Table.CommandOption.IS_REQUIRED);
//				String optionTypeKey = rs.getString(Table.CommandOption.TYPE_KEY);
//				OptionType optionType = null;
//
//				for (OptionType type : OptionType.values())
//				{
//					if (type.name().equals(optionTypeKey))
//						optionType = type;
//				}
//
//				SlashCommand command = new SlashCommand();
//				command.setName(name);
//				command.setDescription(description);
//
//				OptionData option = new OptionData(
//					optionType,
//					optionName,
//					optionDescription,
//					optionIsRequired
//					// isAutocomplete
//				);
//
//				command.addOption(option);
//				System.out.println(command.getName() + " added to command list");
//				SlashCommandManager.add(command);
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}
	
	/**
	 * Adds every custom slash command from the database to the {@link SlashCommandManager}
	 */
	public static void loadSlashCommand() {
		try {
			ResultSet rs = new DatabaseQuery(Table.Command.SELF)
				.select()
				.executeQuery();
			
			while (rs.next()) {
				String commandId = rs.getString(Table.define(Table.Command.SELF, Table.Command.ID));
				String commandName = rs.getString(Table.define(Table.Command.SELF, Table.Command.NAME));
				String commandDescription = rs.getString(Table.Command.DESCRIPTION);
				
				SlashCommand slashCommand = new SlashCommand();
				slashCommand.setName(commandName);
				slashCommand.setDescription(commandDescription);
				loadSlashCommandOptions(slashCommand, commandId);
				SlashCommandManager.add(slashCommand);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadSlashCommandOptions(SlashCommand slashCommand, String slashCommandId) {
		try {
			
			ResultSet rs = new DatabaseQuery(Table.CommandOption.SELF)
				.select()
				.where(Table.CommandOption.COMMAND_ID, DatabaseQuery.Operator.EQUALS, slashCommandId)
				.orderBy(Table.CommandOption.IS_REQUIRED, DatabaseQuery.OrderBy.DESCENDED)
				.executeQuery();
			
			while (rs.next()) {
				String optionId = rs.getString(Table.define(Table.CommandOption.SELF, Table.CommandOption.ID));
				String optionName = rs.getString(Table.define(Table.CommandOption.SELF, Table.CommandOption.NAME));
				String optionDescription = rs.getString(Table.define(Table.CommandOption.SELF, Table.CommandOption.DESCRIPTION));
				boolean optionIsRequired = rs.getBoolean(Table.CommandOption.IS_REQUIRED);
				String optionTypeKey = rs.getString(Table.CommandOption.TYPE_KEY);
				OptionType optionType = OptionType.STRING; // Default = String
				List<String> autocompleteData = loadSlashCommandOptionChoices(optionId);
				
				for (OptionType type : OptionType.values())
				{
					if (type.name().equals(optionTypeKey))
						optionType = type;
				}
				
				OptionData option = new OptionData(
					optionType,
					optionName,
					optionDescription,
					optionIsRequired,
					!autocompleteData.isEmpty() // true if not empty, false if empty
				);
				slashCommand.addOption(option);
				slashCommand.addAutocomplete(optionName, autocompleteData);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Creates a list of all auto-complete values that an option has<br>
	 * Every choice is saved as a String in the database. The value should be cast later on, depending on the option type the choice belongs to
	 * @param optionId
	 * @return
	 */
	private static List<String> loadSlashCommandOptionChoices(String optionId) {
		try {
			List<String> choiceData = new ArrayList<>();
			
			ResultSet rs = new DatabaseQuery(Table.CommandOptionChoice.SELF)
				.select()
				.where(Table.CommandOption.ID, DatabaseQuery.Operator.EQUALS, optionId)
				.executeQuery();
			
			while (rs.next()) {
				String choiceValue = rs.getString(Table.CommandOptionChoice.VALUE);
				
				choiceData.add(choiceValue);
			}
			
			return choiceData;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
