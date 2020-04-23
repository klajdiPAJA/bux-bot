package com.bux.tradebot.dto;

import lombok.Value;

@Value
public class OpenPositionRequest {

    String productId;
    Wallet investingAmount;
    int leverage;
    Direction direction;
    SourceType sourceType;
}
