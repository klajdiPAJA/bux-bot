package com.bux.tradebot;

import com.bux.tradebot.dto.MessageWrapper;
import com.bux.tradebot.service.MessageProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;
import org.springframework.beans.factory.annotation.Value;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ClientEndpoint(configurator = WebsocketClientConfigurator.class)
@Log4j2
@RequiredArgsConstructor
public class WebsocketClient {
    final MessageProcessor processor;

    @Value("${bux.ws.url}")
    @Setter
    private String url;
    Session connection;

    public void start() throws URISyntaxException, DeploymentException, InterruptedException, IOException {
        log.info("Starting websocket connection on {}... ", url);
        ClientManager client = ClientManager.createClient();
        client.getProperties().put(ClientProperties.REDIRECT_ENABLED, true);
        connection = client.connectToServer(this, new URI(url));
        //lock the application as long as the connection is open
        while (connection.isOpen()) {
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        log.debug("Connected ... " + session.getId());
    }

    @OnMessage
    public String onMessage(String message, Session session) throws JsonProcessingException {
        log.debug("Received ...." + message);
        processor.process(new MessageWrapper(message, session));
        return message;
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) throws IOException {
        log.info(String.format("Session %s close because of %s", session.getId(), closeReason));
    }

}