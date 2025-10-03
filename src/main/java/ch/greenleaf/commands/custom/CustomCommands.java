package ch.greenleaf.commands.custom;

import ch.greenleaf.CommandManager;
import ch.greenleaf.DatabaseQuery;
import ch.greenleaf.Table;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomCommands {

	private static List<Custom> commands = new ArrayList<>();
	
	public static void load() {
		try {
			ResultSet rs = new DatabaseQuery(Table.Command.SELF)
				.join( // Select any options a command has, if none, only show command without options
					DatabaseQuery.JoinType.LEFT, // <- Left: even without options a command should be shown
					Table.CommandOption.SELF,
					Table.define(Table.Command.SELF, Table.Command.ID), DatabaseQuery.Operator.EQUALS, Table.CommandOption.COMMAND_ID
				)
				.select()
				.executeQuery();
			
			while (rs.next()) {
				String name = rs.getString(Table.define(Table.Command.SELF, Table.Command.NAME));
				String description = rs.getString(Table.Command.DESCRIPTION);
				String optionName = rs.getString(Table.define(Table.CommandOption.SELF, Table.CommandOption.NAME));
				String optionDescription = rs.getString(Table.define(Table.CommandOption.SELF, Table.CommandOption.DESCRIPTION));
				boolean optionIsRequired = rs.getBoolean(Table.CommandOption.IS_REQUIRED);
				String optionTypeKey = rs.getString(Table.CommandOption.TYPE_KEY);
				OptionType optionType = null;
				
				for (OptionType type : OptionType.values())
				{
					if (type.name().equals(optionTypeKey))
						optionType = type;
				}
				
				Custom command = new Custom();
				command.setName(name);
				command.setDescription(description);
				
				OptionData option = new OptionData(
					optionType,
					optionName,
					optionDescription,
					optionIsRequired
				);
				
				command.addOption(option);
				commands.add(command);
				System.out.println(command.getName() + " added to command list");
				CommandManager.add(command);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
