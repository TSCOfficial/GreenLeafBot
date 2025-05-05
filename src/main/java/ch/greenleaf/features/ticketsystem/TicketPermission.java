package ch.greenleaf.features.ticketsystem;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.util.*;

class TicketPermission{

    private ButtonInteractionEvent interactionEvent;
    private Map<Member, List<Permission>> memberPermissions = new HashMap<Member, List<Permission>>();
    private Map<Role, List<Permission>> rolePermissions = new HashMap<Role, List<Permission>>();
    private long ticketChannelId;
    private Member ticketOwner;

    TicketPermission(ButtonInteractionEvent event, long channelId){
        this.interactionEvent = event;
        this.ticketChannelId = channelId;

        this.ticketOwner = event.getMember();

        setDefaultPermissions();
        setPermissions();
    }

    private void setDefaultPermissions(){
        // add default permissions
        List<Permission> defaultPermissions = new ArrayList<>();

        defaultPermissions.add(Permission.MESSAGE_SEND);
        defaultPermissions.add(Permission.MESSAGE_ATTACH_FILES);
        defaultPermissions.add(Permission.VIEW_CHANNEL);
        defaultPermissions.add(Permission.MESSAGE_HISTORY);

        memberPermissions.put(ticketOwner, defaultPermissions);
    }
    /**
     * Sets the given {@link Permission} to the given {@link TicketChannel}.
     *
     */
    void setPermissions(){
        System.out.println("4 Ticket permissions");
        if (!this.memberPermissions.isEmpty()){

            for (Map.Entry<Member, List<Permission>> entry : this.memberPermissions.entrySet()){
                Member member = entry.getKey();
                List<Permission> permissionList = entry.getValue();

                System.out.println(entry.getKey());

                long channelId = this.ticketChannelId;

                EnumSet<Permission> permissionEnumSet = EnumSet.noneOf(Permission.class); // make an empty enumset
                permissionEnumSet.addAll(permissionList); // add varargs to it

                System.out.println(permissionEnumSet);
                this.interactionEvent.getGuild().getGuildChannelById(channelId).getPermissionContainer().upsertPermissionOverride(member).setAllowed(permissionEnumSet).queue();

            }
        }

        if (!this.rolePermissions.isEmpty()){

            for (Map.Entry<Role,List<Permission>> entry : this.rolePermissions.entrySet()){
                System.out.println(entry.getKey());

                for (Permission entryPermissions : entry.getValue()){
                    System.out.println(entryPermissions);
                }
            }
        }
    }

}
