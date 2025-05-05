package ch.greenleaf;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class BackendDispatcher {

    private static CommandManager commandManager;

    public static void setCommandManager(CommandManager cm) {
        commandManager = cm;
    }

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final Map<String, Consumer<JsonNode>> routes = new HashMap<>();

    // Command connection, put(actionName, ... -> execute(registeredCommandName, ...)
    static {
        routes.put("sendMessage", payload -> commandManager.execute("message", payload));
    }

    /**
     * Handles the incoming messages from the WebSocket.
     * @param json WebSocket data
     */
    public static void handleMessage(String json) {
        try {
            System.out.println(json);
            JsonNode node = mapper.readTree(json);
            String type = node.get("type").asText();
            JsonNode payload = node.get("payload");

            Consumer<JsonNode> route = routes.get(type);
            if (route != null) {
                route.accept(payload);
            } else {
                System.err.println("Unknown command type: " + type);
            }

        } catch (Exception e) {
            System.err.println("Fehler beim Verarbeiten von Backend-Message:");
            e.printStackTrace();
        }
    }
}
