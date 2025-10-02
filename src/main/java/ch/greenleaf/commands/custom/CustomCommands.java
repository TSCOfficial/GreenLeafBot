package ch.greenleaf.commands.custom;

import ch.greenleaf.CommandManager;
import ch.greenleaf.DatabaseQuery;
import ch.greenleaf.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomCommands {

	private static List<Custom> commands = new ArrayList<>();
	
	public static void load() {
		try {
			ResultSet rs = new DatabaseQuery(Table.Command.SELF)
				.select()
				.executeQuery();
			
			while (rs.next()) {
				String name = rs.getString(Table.Command.NAME);
				String description = rs.getString(Table.Command.DESCRIPTION);
				
				Custom command = new Custom();
				command.setName(name);
				command.setDescription(description);
				commands.add(command);
				System.out.println(command.getName() + " added to command list");
				CommandManager.add(command);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
