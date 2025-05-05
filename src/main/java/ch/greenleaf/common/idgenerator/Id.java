package ch.greenleaf.common.idgenerator;

import java.util.UUID;

/**
 * Generate an ID for a Button.
 * The Button ID offers the possibility to search for a specific set of settings, that was set by the guild
 * administrator, in the database.
 */
public class Id {

    public static String generate(Object object){
        StringBuilder id = new StringBuilder();
        id.append(object.getClass().getSimpleName());
        id.append("-");
        id.append(UUIDString());
        return id.toString();
    }

    public static String generate(Object object, String guildId){
        StringBuilder id = new StringBuilder();
        id.append(object.getClass().getSimpleName());
        id.append("-");
        id.append(guildId);
        id.append("-");
        id.append(UUIDString());
        return id.toString();
    }

    public static String UUIDString(){
        return UUID.randomUUID().toString();
    }
}
