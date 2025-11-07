package ch.greenleaf.features.teamoverview;

import ch.greenleaf.Client;
import ch.greenleaf.Database;
import ch.greenleaf.DatabaseQuery;
import ch.greenleaf.Table;
import ch.greenleaf.components.embed.Embed;
import ch.greenleaf.components.message.Message;
import ch.greenleaf.interactions.actions.list.SendMessage;
import net.dv8tion.jda.api.entities.Role;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

public class TeamOverview {
	
	private long guild_id;
	private long channel_id;
	private Color color;
	private String title;
	private String not_found;
	private boolean showMemberCount;
	
	private List<TeamRole> roles = new ArrayList<>();
	
	public TeamOverview(long guild_id) {
		this.guild_id = guild_id;
		fetchDatabase();
		generateOverview();
	}
	
	public void fetchDatabase() {
		try {
			ResultSet rs = new DatabaseQuery(Table.TeamOverview.SELF)
				.join(
					Table.TeamOverviewRole.SELF,
					Table.define(Table.TeamOverview.SELF, Table.TeamOverview.ID),
					DatabaseQuery.Operator.EQUALS,
					Table.TeamOverviewRole.TEAMOVERVIEW_ID
				)
				.join(
					Table.Role.SELF,
					Table.define(Table.TeamOverviewRole.SELF, Table.TeamOverviewRole.ROLE_ID),
					DatabaseQuery.Operator.EQUALS,
					Table.define(Table.Role.SELF, Table.Role.ID)
				).where(Table.TeamOverview.GUILD_ID, DatabaseQuery.Operator.EQUALS, guild_id)
				.executeQuery();
			
			System.out.println("Fetch terminated");
			while (rs.next()) {
				channel_id = rs.getLong(Table.TeamOverview.CHANNEL_ID);
				color = Color.decode(rs.getString(Table.TeamOverview.COLOR));
				title = rs.getString(Table.TeamOverview.TITLE);
				not_found = rs.getString(Table.TeamOverview.NO_USER_FOUND_ERROR);
				showMemberCount = rs.getBoolean(Table.TeamOverview.DISPLAY_MEMBER_COUNT);
				System.out.println("teamoverview terminated");
				
				String icon_name = rs.getString(Table.TeamOverviewRole.ICON_NAME);
				String role_custom_name = rs.getString(Table.TeamOverviewRole.CUSTOM_NAME);
				long icon_id = rs.getLong(Table.TeamOverviewRole.ICON_ID);
				boolean icon_is_animated = rs.getBoolean(Table.TeamOverviewRole.ICON_IS_ANIMATED);
				long role_id = rs.getLong(Table.define(Table.Role.SELF, Table.Role.ROLE_ID));
				
				System.out.println("Roles terminated");
				
				TeamRole teamrole = new TeamRole(role_id, role_custom_name, icon_name, icon_id, icon_is_animated);
				roles.add(teamrole);
				
				System.out.println("Added terminated");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void generateOverview() {
		Embed embed = new Embed();
		roles.forEach(teamRole -> {
			Role role = Client.client.getShardManager().getRoleById(teamRole.role_id());
			List<String> membersWithRole = Client.client.getShardManager().getGuildById(guild_id).getMembersWithRoles(role).stream().map(member -> member.getAsMention()).toList();
			
			String fieldValue = String.join(", ", membersWithRole);
			if (membersWithRole.isEmpty()) {
				fieldValue = not_found;
			}
			
			StringBuilder fieldTitle = new StringBuilder();
			
			if (teamRole.icon_name() != null && teamRole.icon_id() != null) {
				fieldTitle.append("<:" + teamRole.icon_name() + ":" + teamRole.icon_id() + "> ");
			}
			
			if (teamRole.custom_name() != null) {
				fieldTitle.append(teamRole.custom_name());
			} else {
				fieldTitle.append(role.getName());
			}
			
			if (showMemberCount) {
				fieldTitle.append(" (" + membersWithRole.size() + ")");
			}
			
			embed.addField(
				fieldTitle.toString(), fieldValue, true
			);
			
			embed.setTitle(title);
			embed.setTimestamp(LocalDateTime.now());
			embed.setColor(color);
		});
		embed.build();
		
		Message msg = new Message();
		msg.addEmbed(embed);
		msg.setChannelId(channel_id);
		
		new SendMessage(msg);
	}
}
