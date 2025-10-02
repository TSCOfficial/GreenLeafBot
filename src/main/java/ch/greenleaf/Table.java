package ch.greenleaf;

/**
 * The collection of all possible database tables and columns
 */
public abstract class Table {
	
	/**
	 * Action table
	 */
	public static final class Action {
		public static final String SELF = "action";
		public static final String ID = "id";
		public static final String TYPE = "type_id";
		public static final String DATASOURCE_ID = "datasource_id";
		
		private Action() {
		
		}
	}
	
	/**
	 * Button table
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
		
		private Button() {
		
		}
	}
	
	/**
	 * Button-Action join table
	 */
	public static final class ButtonAction {
		public static final String SELF = "button_action";
		public static final String BUTTON_ID = "button_id";
		public static final String ACTION_ID = "action_id";
		
		private ButtonAction() {
		
		}
	}
	
	/**
	 * Embed table
	 */
	public static final class Embed {
		public static final String SELF = "embed";
		public static final String ID = "id";
		public static final String TITLE = "title";
		public static final String DESCRIPTION = "description";
		public static final String AUTHOR = "author_name";
		public static final String FOOTER = "footer_text";
		
		private Embed() {
		
		}
	}
	
	/**
	 * Field-embed table
	 */
	public static final class FieldEmbed {
		public static final String SELF = "field_embed";
		public static final String ID = "id";
		public static final String EMBED_ID = "embed_id";
		public static final String NAME = "name";
		public static final String VALUR = "value";
		
		private FieldEmbed() {
		
		}
	}
	
	/**
	 * Message table
	 */
	public static final class Message {
		public static final String SELF = "message";
		public static final String ID = "id";
		public static final String TEXT = "text";
		public static final String CHANNEL_ID = "channel_id";
		public static final String IS_EPHEMERAL = "is_ephemeral";
		
		private Message() {
		
		}
	}
	
	/**
	 * Message-Embed join table
	 */
	public static final class MessageEmbed {
		public static final String SELF = "message_embed";
		public static final String MESSAGE_ID = "message_id";
		public static final String EMBED_ID = "embed_id";
		
		private MessageEmbed() {
		
		}
	}
	
	public static final class Role {
		public static final String SELF = "role";
		public static final String ID = "id";
		public static final String ROLE_ID = "role_id";
		
		private Role() {
		
		}
	}
	
	public static final class Command {
		public static final String SELF = "command";
		public static final String ID = "id";
		public static final String NAME = "name";
		public static final String DESCRIPTION = "description";
		
		private Command() {
		
		}
	}
	
	public static final class CommandAction {
		public static final String SELF = "command_action";
		public static final String COMMAND_ID = "command_id";
		public static final String ACTION_ID = "action_id";
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
