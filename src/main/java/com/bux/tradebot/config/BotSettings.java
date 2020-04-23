package com.bux.tradebot.config;

import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Builder
public class BotSettings {
    String productId;
    Double buyPrice;
    Double upperLimitSellPrice;
    Double lowerLimitSellPrice;

}
