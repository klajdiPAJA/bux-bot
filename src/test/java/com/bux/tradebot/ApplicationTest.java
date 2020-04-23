package com.bux.tradebot;

import com.bux.tradebot.config.ApplicationConfig;
import com.bux.tradebot.service.BaseMockitoTest;
import org.junit.Test;

public class ApplicationTest extends BaseMockitoTest {
    private ApplicationConfig applicationConfig = new ApplicationConfig();

    @Test(expected = IllegalArgumentException.class)
    public void main_whenInvalidLowerLimitPrice_shouldThrowIllegallArgument() {
        applicationConfig.botSettings(defaultProductId, defaultBuyPrice, defaultBuyPrice - 1, defaultLowerSellPrice);

    }

    @Test(expected = IllegalArgumentException.class)
    public void main_whenInvalidUpperLimitPrice_shouldThrowIllegallArgument() {
        applicationConfig.botSettings(defaultProductId, defaultBuyPrice, defaultUpperSellPrice, defaultBuyPrice + 1);
    }

    @Test
    public void main_validSettings_shouldBuildBotSettings() {
        applicationConfig.botSettings(defaultProductId, defaultBuyPrice, defaultUpperSellPrice, defaultLowerSellPrice);
    }
}
