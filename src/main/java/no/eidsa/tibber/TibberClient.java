package no.eidsa.tibber;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import javax.annotation.PreDestroy;
import java.net.URI;

public class TibberClient extends WebSocketClient {

    private final String homeId;
    private final String token;

    public TibberClient(URI serverUri, Draft protocolDraft, String token, String homeId) {
        super(serverUri, protocolDraft);
        this.token = token;
        this.homeId = homeId;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        send("{\"type\":\"init\",\"payload\":\"token=" + token + "\"}");

        String query = "{\"query\":\"subscription{\\n  liveMeasurement(homeId:\\\"" + homeId + "\\\"){\\n    timestamp\\n    power\\n    accumulatedConsumption\\n    accumulatedCost\\n    minPower\\n    averagePower\\n    maxPower\\n  }\\n}\\n\",\"variables\":null,\"type\":\"subscription_start\",\"id\":0}";
        send(query);
    }

    @Override
    public void onMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println(s);
        send("{\"id\":0,\"type\":\"subscription_end\"}");
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }

    @PreDestroy
    public void destroy() {
        if (isOpen()) {
            close();
        }
    }

}
