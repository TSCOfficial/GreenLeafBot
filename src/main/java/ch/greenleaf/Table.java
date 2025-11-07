package ch.greenleaf;

/**
 * The collection of all possible database tables and columns
 */
public abstract class Table {
	
	/**
	 * Actions
	 */
	public static final class Action {
		public static final String SELF = "action";
		public static final String ID = "id";
		public static final String TYPE_ID = "type_id";
		public static final String DATASOURCE_ID = "datasource_id";
	}
	
	/**
	 * Buttons
	 */
	public static final class Button {
		public static final String SELF = "button";
		public static final String ID = "id";
		public static final String IDENTIFIER = "identifier";
		public static final String STYLE_ID = "style_id";
		public static final String LABEL = "label";
		public static final String EMOJI = "emoji";
		public static final String URL = "url";
		public static final String IS_DISABLED = "is_disabled";
	}
	
	/**
	 * Joins the buttons with actions
	 */
	public static final class ButtonAction {
		public static final String SELF = "button_action";
		public static final String BUTTON_ID = "button_id";
		public static final String ACTION_ID = "action_id";
	}
	
	/**
	 * Embeds
	 */
	public static final class Embed {
		public static final String SELF = "embed";
		public static final String ID = "id";
		public static final String TITLE = "title";
		public static final String DESCRIPTION = "description";
		public static final String AUTHOR = "author_name";
		public static final String FOOTER = "footer_text";
	}
	
	/**
	 * Embed fields
	 */
	public static final class FieldEmbed {
		public static final String SELF = "field_embed";
		public static final String ID = "id";
		public static final String EMBED_ID = "embed_id";
		public static final String NAME = "name";
		public static final String VALUR = "value";
	}
	
	/**
	 * Messages
	 */
	public static final class Message {
		public static final String SELF = "message";
		public static final String ID = "id";
		public static final String TEXT = "text";
		public static final String CHANNEL_ID = "channel_id";
		public static final String IS_EPHEMERAL = "is_ephemeral";
	}
	
	/**
	 * Joins the messages with the embeds
	 */
	public static final class MessageEmbed {
		public static final String SELF = "message_embed";
		public static final String MESSAGE_ID = "message_id";
		public static final String EMBED_ID = "embed_id";
	}
	
	/**
	 * Roles
	 */
	public static final class Role {
		public static final String SELF = "role";
		public static final String ID = "id";
		public static final String ROLE_ID = "role_id";
	}
	
	/**
	 * Commands
	 */
	public static final class Command {
		public static final String SELF = "command";
		public static final String ID = "id";
		public static final String NAME = "name";
		public static final String DESCRIPTION = "description";
	}
	
	/**
	 * Joins the commands with the actions
	 */
	public static final class CommandAction {
		public static final String SELF = "command_action";
		public static final String COMMAND_ID = "command_id";
		public static final String ACTION_ID = "action_id";
	}
	
	/**
	 * Command options
	 */
	public static final class CommandOption {
		public static final String SELF = "command_option";
		public static final String ID = "id";
		public static final String COMMAND_ID = "command_id";
		public static final String TYPE_KEY = "type_key";
		public static final String NAME = "name";
		public static final String DESCRIPTION = "description";
		public static final String MIN_LENGTH = "min_length";
		public static final String MAX_LENGTH = "max_length";
		public static final String MIN_VALUE = "min_value";
		public static final String MAX_VALUE = "max_value";
		public static final String IS_REQUIRED = "is_required";
	}
	
	/**
	 * Command option autocomplete
	 */
	public static final class CommandOptionChoice {
		public static final String SELF = "command_option_choice";
		public static final String ID = "id";
		public static final String COMMAND_OPTION_ID = "command_option_id";
		public static final String VALUE = "value";
	}
	
	/**
	 * Action types
	 */
	public static final class ActionType {
		public static final String SELF = "action_type";
		public static final String ID = "id";
	}
	
	/**
	 * Action type variables
	 */
	public static final class ActionTypeVariable {
		public static final String SELF = "action_type_variable";
		public static final String ID = "id";
		public static final String ACTION_TYPE_ID = "action_type_id";
		public static final String VARIABLE_KEY = "variable_key";
	}
	
	/**
	 * Joins the command options with the action type variables
	 */
	public static final class CommandOptionActionTypeVariable {
		public static final String SELF = "command_option_action_type_variable";
		public static final String ACTION_TYPE_VARIABLE_ID = "action_type_variable_id";
		public static final String COMMAND_OPTION_ID = "command_option_id";
		public static final String ACTION_ID = "action_id";
	}
	
	/**
	 * Team overview table
	 */
	public static final class TeamOverview {
		public static final String SELF = "teamoverview";
		public static final String ID = "id";
		public static final String GUILD_ID = "guild_id";
		public static final String CHANNEL_ID = "channel_id";
		public static final String COLOR = "color";
		public static final String TITLE = "title";
		public static final String NO_USER_FOUND_ERROR = "no_user_found_error";
		public static final String DISPLAY_MEMBER_COUNT = "display_member_count";
	}
	
	/**
	 * Joins team overview with role
	 */
	public static final class TeamOverviewRole {
		public static final String SELF = "teamoverview_role";
		public static final String TEAMOVERVIEW_ID = "teamoverview_id";
		public static final String ROLE_ID = "role_id";
		public static final String ICON_NAME = "icon_name";
		public static final String ICON_ID = "icon_id";
		public static final String ICON_IS_ANIMATED= "icon_is_animated";
	}
	
	/**
	 * This method allows to clear ambiguous column calls by using the SQL <code>table.column</code> syntax
	 * @param table The table name
	 * @param column The column name
	 * @return The <code>table.column</code> SQL syntax variant
	 */
	public static String define(String table, String column) {
		return table + "." + column;
	}
}
