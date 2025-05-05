package ch.greenleaf;

import ch.greenleaf.features.commands.Message;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class BackendDispatcher {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final Map<String, Consumer<JsonNode>> routes = new HashMap<>();

    static {
        routes.put("sendEmbed", BackendDispatcher::handleSendEmbed);
        routes.put("sendMessage", Message::execute);
        // Weitere Befehle wie nötig
    }

    /**
     *
     * @param json
     */
    public static void handleMessage(String json) {
        try {
            JsonNode node = mapper.readTree(json);
            String type = node.get("type").asText();

            switch (type) {
                case "sendEmbed" -> handleSendEmbed(node.get("payload"));
                case "sendMessage" -> Message.handleFromBackend(node.get("payload"));
                // Weitere cases: ticketOpen, userNotify, log, etc.
                default -> System.out.println("Unbekannter Typ: " + type);
            }

        } catch (Exception e) {
            System.err.println("Fehler beim Verarbeiten von Backend-Message:");
            e.printStackTrace();
        }
    }
}
