package com.bux.tradebot.service;

import com.bux.tradebot.config.BotSettings;
import com.bux.tradebot.dto.*;
import com.bux.tradebot.rest.TradeRestApi;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class TradeService {
    private final BotSettings botSettings;
    private final TradeRestApi restApi;
    private final Wallet standardInvestment;
    @Getter
    private OpenPositionResponse openPosition;

    public void processQuote(Quote quote) {
        String securityId = quote.getSecurityId();
        if (!isProductSubscribed(securityId)) {
            log.debug("Cannot process quote, user is not subscribed to openPositionProduct.");
            return;
        }
        Double price = quote.getCurrentPrice();

        if (shouldOpenPosition(price)) {
            OpenPositionRequest openPositionRequest = new OpenPositionRequest(securityId, standardInvestment, 2, Direction.BUY, SourceType.OTHER);
            Optional<OpenPositionResponse> openPositionResponse = restApi.openPosition(openPositionRequest);
            OpenPositionResponse openPosition = openPositionResponse.orElse(null);
            log.debug("Successfully opened position {}", openPosition);
            storePosition(openPosition);
        } else if (shouldClosePosition(price)) {
            log.debug("Selling Securities at price {}", price);
            if (restApi.closePosition(openPosition))
                resetPosition();
        } else
            log.debug("Ignoring quote price: {}, openedPositions: {}", price, openPosition);
    }


    private boolean isProductSubscribed(String productId) {
        return botSettings.getProductId().equals(productId);
    }


    private boolean shouldClosePosition(Double currentPrice) {
        return openPosition != null && (Double.compare(currentPrice, botSettings.getLowerLimitSellPrice()) <= 0 ||
                Double.compare(currentPrice, botSettings.getUpperLimitSellPrice()) >= 0);
    }

    private boolean shouldOpenPosition(Double currentPrice) {
        return openPosition == null && Double.compare(botSettings.getBuyPrice(), currentPrice) == 0;
    }


    private void storePosition(OpenPositionResponse position) {
        this.openPosition = position;
    }

    private void resetPosition() {
        storePosition(null);
    }

}
