package com.bux.tradebot.websocket;


import com.bux.tradebot.WebsocketClient;
import com.bux.tradebot.service.MessageProcessor;
import org.glassfish.tyrus.server.Server;
import org.glassfish.tyrus.test.tools.TestContainer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WebsocketClientTest extends TestContainer {
    @Mock
    MessageProcessor messageProcessor;

    @InjectMocks
    private WebsocketClient websocketClient;

    @Test
    public void start_WhenMockServerConnected_ShouldProcessReceivedMessage() throws DeploymentException, IOException, URISyntaxException, InterruptedException {
        //given
        final Server server = startServer(WebsocketMockServer.class);
        websocketClient.setUrl(getURI(WebsocketMockServer.class).toString());
        //when
        websocketClient.start();
        verify(messageProcessor, times(1)).process(any());
        //then
        server.stop();
    }
}