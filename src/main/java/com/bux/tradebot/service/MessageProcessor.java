package com.bux.tradebot.service;

import com.bux.tradebot.dto.Message;
import com.bux.tradebot.dto.MessageWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import static com.bux.tradebot.dto.EventType.*;

@Log4j2
@RequiredArgsConstructor
@Component
public class MessageProcessor {
    final SubscriptionService subscriptionService;
    final TradeService tradeService;

    public void process(MessageWrapper messageWrapper) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(messageWrapper.getMessage(), Message.class);
        String event = message.getEvent();

        log.debug("Received message for {} event", event);

        if (CONNECTION_SUCCESS.getValue().equals(event)) {
            subscriptionService.subscribe(messageWrapper.getSession());
        } else if (TRADE_QUOTE.getValue().equals(event)) {
            tradeService.processQuote(message.getQuote());
        } else if (CONNECTION_FAIL.getValue().equals(event)) {
            log.warn("Received fail event from websocket. For more see message: {}", messageWrapper.getMessage());
        }

    }


}
