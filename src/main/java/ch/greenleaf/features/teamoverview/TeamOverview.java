package ch.greenleaf.features.teamoverview;

import ch.greenleaf.Client;
import ch.greenleaf.components.embed.Embed;
import ch.greenleaf.components.embed.Field;
import ch.greenleaf.components.message.Message;
import ch.greenleaf.interactions.actions.list.SendMessage;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.*;

public class TeamOverview {
	
	private long guild_id = 1228461292440780801L;
	private long channel_id = 1231933541017845882L;
	private String not_found = "User not found";
	private boolean showMemberCount = true;
	
	private Map<String, Long> roles = Map.of(
		"<:dot:1233080294001606716>", 1228461719236509817L, // Devportal Administrator
		"<:icon_subtext:1261435652260499477>", 1229002243072725073L, // Devportal Moderator
		"", 1233070430584311909L // GreenLeaf management
		);
	
	public void generateOverview() {
		Embed embed = new Embed();
		roles.forEach((icon, roleId) -> {
			Role role = Client.client.getShardManager().getRoleById(roleId);
			List<String> membersWithRole = Client.client.getShardManager().getGuildById(guild_id).getMembersWithRoles(role).stream().map(member -> member.getAsMention()).toList();
			
			String fieldValue = String.join(", ", membersWithRole);
			if (membersWithRole.isEmpty()) {
				fieldValue = not_found;
			}
			
			StringBuilder fieldTitle = new StringBuilder()
				.append(icon)
					.append(role.getName());
			
			if (showMemberCount) {
				fieldTitle.append(" (" + membersWithRole.size() + ")");
			}
			
			embed.addField(
				fieldTitle.toString(), fieldValue, true
			);
		});
		embed.build();
		
		Message msg = new Message();
		msg.addEmbed(embed);
		msg.setChannelId(channel_id);
		
		new SendMessage(msg);
	}
}
