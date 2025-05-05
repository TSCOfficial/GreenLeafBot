package ch.greenleaf;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WebSocketClientHandler extends WebSocketClient {

    public interface MessageListener {
        void onMessage(String message);
    }

    private MessageListener listener;

    public WebSocketClientHandler(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("[WebSocket] Verbunden mit Backend.");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("[WebSocket] Nachricht erhalten: " + message);
        if (listener != null) {
            listener.onMessage(message);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("[WebSocket] Verbindung geschlossen: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public void setMessageListener(MessageListener listener) {
        this.listener = listener;
    }
}
