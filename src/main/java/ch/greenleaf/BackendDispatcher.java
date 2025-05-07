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
     * @param message WebSocket message
     */
    public static void handleMessage(String message) {
        try {
            System.out.println(message);
            JsonNode node = mapper.readTree(message);
            String type = node.get("destination").asText();
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

    public static void sendMessage(String message) {
        Client.webSocket.send(message);
    }

    public static void responseSuccess(JsonNode payload) {
        response("success", payload);
    }

    public static void responseError(JsonNode payload) {
        response("error", payload);
    }

    private static void response(String status, JsonNode payload) {
        JsonNode response = mapper.createObjectNode()
                .put("type", "response")
                .put("status", status)
                .set("payload", payload);

        BackendDispatcher.sendMessage(response.toString());
    }

}
