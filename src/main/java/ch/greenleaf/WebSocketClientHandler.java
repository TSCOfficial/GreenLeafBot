package ch.greenleaf;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * Handle websocket client connections and
 */
public class WebSocketClientHandler extends WebSocketClient {

    public WebSocketClientHandler(URI serverUri) {
        super(serverUri);
    }
	
	/**
	 * Execute something when the websocket connects to a remote client
	 * @param handshake The handshake
	 */
    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("[WebSocket] Verbunden mit Backend.");
    }

    /**
     * React on received messages.
     * @param message The received message
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
	
	/**
	 * Execute something when the websocket connection closes
	 * @param code Websocket code
	 * @param reason Closing reason
	 * @param remote Whether the remote client closed or this client
	 */
    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("[WebSocket] Verbindung geschlossen: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }
}
