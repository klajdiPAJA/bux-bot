package com.bux.tradebot.service;

import com.bux.tradebot.dto.Subscription;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.websocket.EncodeException;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.util.Collections;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionServiceMockitoTest extends BaseMockitoTest {

    Subscription mockSubscription;
    SubscriptionService subscriptionService;
    @Mock
    Session socketSession;
    @Mock
    SessionRemoteMock sessionRemoteMock;
    ObjectMapper objectMapper = new ObjectMapper();

    public SubscriptionServiceMockitoTest() {
        super();
    }

    @Before
    public void setup() {
        mockSubscription = new Subscription(SubscriptionService.transformProductIds(Collections.singleton(defaultProductId)), null);
        subscriptionService = new SubscriptionService(objectMapper, defaultSettings);
    }

    @Test
    public void subscribe_ReceivedConnectedMessage_shouldSubscribeToProductId() throws IOException {
        //given
        when(socketSession.getBasicRemote()).thenReturn(sessionRemoteMock);
        String subscriptionSendMessage = objectMapper.writeValueAsString(mockSubscription);
        //when
        subscriptionService.subscribe(socketSession);
        //then
        verify(sessionRemoteMock, times(1)).sendText(subscriptionSendMessage);

    }


}

class SessionRemoteMock implements RemoteEndpoint.Basic {

    @Override
    public void sendText(String text) throws IOException {

    }

    @Override
    public void sendBinary(ByteBuffer data) throws IOException {

    }

    @Override
    public void sendText(String partialMessage, boolean isLast) throws IOException {

    }

    @Override
    public void sendBinary(ByteBuffer partialByte, boolean isLast) throws IOException {

    }

    @Override
    public OutputStream getSendStream() throws IOException {
        return null;
    }

    @Override
    public Writer getSendWriter() throws IOException {
        return null;
    }

    @Override
    public void sendObject(Object data) throws IOException, EncodeException {

    }

    @Override
    public boolean getBatchingAllowed() {
        return false;
    }

    @Override
    public void setBatchingAllowed(boolean allowed) throws IOException {

    }

    @Override
    public void flushBatch() throws IOException {

    }

    @Override
    public void sendPing(ByteBuffer applicationData) throws IOException, IllegalArgumentException {

    }

    @Override
    public void sendPong(ByteBuffer applicationData) throws IOException, IllegalArgumentException {

    }
}
