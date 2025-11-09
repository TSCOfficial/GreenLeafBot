package ch.greenleaf.features.appinfo;

import ch.greenleaf.components.embed.Embed;
import ch.greenleaf.components.message.Message;
import ch.greenleaf.features.Feature;
import ch.greenleaf.interactions.actions.list.SendMessage;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;

import java.awt.*;

// TODO create an interface/abstract class to manage all features the same way. Every feature has at least a getTemplate() which creates the stuff, and a fetchDatabase() that gets the data from the DB (using the guild_id given by the constructor)
public class AppInfo extends Feature {
	
	private long channel_id;
	
	public AppInfo(long guild_id) {
		super(guild_id);
	}
	
	public AppInfo(GenericInteractionCreateEvent event) {
		super(event);
	}
	
	@Override
	protected void fetchDatabase() {
		channel_id = event.getChannelIdLong();
	}
	
	@Override
	protected void getTemplate() {
		Embed embed = new Embed();
		
		embed.setTitle("Application informations");
		embed.setColor(Color.decode("#6bd35e"));
		embed.addField("Developer", "<@618876411905835018>", true);
		embed.addField("Commands", "Slash-/Application", true);
		
		Message msg = new Message();
		msg.addEmbed(embed);
		msg.setChannelId(channel_id);
		
		new SendMessage(msg);
	}
}
