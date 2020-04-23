package com.bux.tradebot.dto;

import lombok.Data;

@Data
public class ClosePositionResponse {

    String id;
    String positionId;
    Wallet profitAndLoss;
    Product product;
    Wallet investingAmount;
    Wallet price;
    int leverage;
    Direction direction;
    long dateCreated;
}
