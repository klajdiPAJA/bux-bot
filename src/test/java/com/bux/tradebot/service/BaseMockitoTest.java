package com.bux.tradebot.service;

import com.bux.tradebot.config.BotSettings;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

public class BaseMockitoTest {
    protected final BotSettings defaultSettings;
    protected String defaultProductId = "dummyId";
    protected Double defaultBuyPrice = 10.0;
    protected Double defaultLowerSellPrice = 1.0;
    protected Double defaultUpperSellPrice = 100.0;

    public BaseMockitoTest() {
        this.defaultSettings = BotSettings.builder().buyPrice(defaultBuyPrice)
                .lowerLimitSellPrice(defaultLowerSellPrice)
                .upperLimitSellPrice(defaultUpperSellPrice)
                .productId(defaultProductId)
                .build();
    }

}
