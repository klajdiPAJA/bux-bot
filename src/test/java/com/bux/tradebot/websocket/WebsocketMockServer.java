package com.bux.tradebot.websocket;


import org.apache.commons.io.IOUtils;

import javax.websocket.CloseReason;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Server endpoint "listening" on path "/echo".
 */
@ServerEndpoint("/subscriptions/me")
public class WebsocketMockServer {

    /**
     * Return websocket connected message to the client once the connection has been established
     * @param session
     * @throws IOException
     */
    @OnOpen
    public void connected(Session session) throws IOException {
        session.getBasicRemote().sendText(IOUtils.resourceToString("/websocket_connected.json", Charset.forName("UTF-8")));
        session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Finished sending message"));
    }
}
