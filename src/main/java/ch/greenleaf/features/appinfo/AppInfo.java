package ch.greenleaf.features.appinfo;

import ch.greenleaf.components.embed.Embed;
import ch.greenleaf.components.message.Message;
import ch.greenleaf.interactions.actions.list.SendMessage;

import java.awt.*;

public class AppInfo {
	
	private long channel_id = 1231933541017845882L;
	
	public AppInfo(long guild_id) {
		getTemplate();
	}
	
	private void getTemplate() {
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
