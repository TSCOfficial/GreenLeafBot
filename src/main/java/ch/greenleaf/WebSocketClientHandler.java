package ch.greenleaf;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WebSocketClientHandler extends WebSocketClient {

    public WebSocketClientHandler(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("[WebSocket] Verbunden mit Backend.");
    }

    /**
     * React on recieved messages.
     * @param message
     */
    @Override
    public void onMessage(String message) {
        System.out.println("[WebSocket] Nachricht erhalten: " + message);
        BackendDispatcher.handleMessage(message);
//        if (listener != null) {
//            listener.onMessage(message);
//            System.out.println("[WebSocket] " + message);
//            BackendDispatcher.handleMessage(message);
//        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("[WebSocket] Verbindung geschlossen: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }
}
