package com.bux.tradebot.dto;

import lombok.Data;

@Data
public class OpenPositionResponse {

    String id;
    String positionId;
    Product product;
    Wallet investingAmount;
    Wallet price;
    int leverage;
    Direction direction;
    long dateCreated;
}
