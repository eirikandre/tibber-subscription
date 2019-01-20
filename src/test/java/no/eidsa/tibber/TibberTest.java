package no.eidsa.tibber;

import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.protocols.Protocol;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

public class TibberTest {

    private static final String TOKEN = "";
    private static final String HOME_ID = "";

    @Test
    public void startClient() throws Exception {
        TibberClient tibberClient = createTibberClient();
        tibberClient.run();
    }

    private TibberClient createTibberClient() throws URISyntaxException {
        String url = "wss://api.tibber.com/v1-beta/gql/subscriptions";

        Draft_6455 draft_ocppOnly = new Draft_6455(Collections.emptyList(), Collections.singletonList(new Protocol("graphql-subscriptions")));

        return new TibberClient(new URI(url), draft_ocppOnly, TOKEN, HOME_ID);
    }

}
