package com.bux.tradebot.service;

import com.bux.tradebot.config.BotSettings;
import com.bux.tradebot.dto.MessageWrapper;
import com.bux.tradebot.dto.Quote;
import junit.runner.BaseTestRunner;
import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.websocket.Session;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MessageProcessorTest extends BaseMockitoTest {
    private static final String connectedMessageName = "websocket_connected";
    private static final String quoteMessageName = "websocket_quote";
    @InjectMocks
    MessageProcessor messageProcessor;
    @Mock
    TradeService tradeService;
    @Mock
    SubscriptionService subscriptionService;
    @Mock
    Session socketSession;

    @Test
    public void process_ReceivedConnectionMessage_shouldSubscribeUserToProduct() throws IOException {
        //when
        messageProcessor.process(new MessageWrapper(loadTestMessage(connectedMessageName), socketSession));
        //then
        verify(subscriptionService, times(1)).subscribe(socketSession);

    }

    @Test
    public void process_ReceivedQuoteMessage_shouldProcessQuote() throws IOException {
        //given
        Quote mockQuote = new Quote("sb26493", 10451.5);
        //when
        messageProcessor.process(new MessageWrapper(loadTestMessage(quoteMessageName), socketSession));
        //then
        verify(tradeService, times(1)).processQuote(mockQuote);
    }

    private String loadTestMessage(String messageName) throws IOException {
        return IOUtils.resourceToString(String.format("/%s.json", messageName), Charset.forName("UTF-8"));
    }
}
