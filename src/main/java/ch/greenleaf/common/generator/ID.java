package ch.greenleaf.common.generator;

import java.util.UUID;

/**
 * Generate an ID for an object.
 */
public class ID {

    public static String generate(Object object){
        StringBuilder id = new StringBuilder();
        id.append(object.getClass().getSimpleName());
        id.append("-");
        id.append(UUIDString());
        return id.toString();
    }

    public static String generate(String value){
        StringBuilder id = new StringBuilder();
        id.append(value);
        id.append("-");
        id.append(UUIDString());
        return id.toString();
    }

    public static String UUIDString(){
        return UUID.randomUUID().toString();
    }
}
