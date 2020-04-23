package com.bux.tradebot.service;

import com.bux.tradebot.config.BotSettings;
import com.bux.tradebot.dto.Subscription;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;


@Log4j2
@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final ObjectMapper objectMapper;
    private final BotSettings botSettings;

    public void subscribe(Session session) {
        try {
            Set<String> ids = transformProductIds(Collections.singleton(botSettings.getProductId()));
            final String subscriptionMessageAsJson = objectMapper.writeValueAsString(new Subscription(ids, null));
            log.info("Subscribing: {}", subscriptionMessageAsJson);
            session.getBasicRemote().sendText(subscriptionMessageAsJson);
        } catch (final IOException e) {
            log.error("Failed to subscribe {} ", e.getMessage(), e);
        }
    }

    public static Set<String> transformProductIds(Set<String> productIds) {
        return productIds.stream().map(s -> "trading.product." + s).collect(Collectors.toSet());

    }
}
