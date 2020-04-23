package com.bux.tradebot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EventType {
  CONNECTION_SUCCESS("connect.connected"),
  CONNECTION_FAIL("connect.failed"),
  TRADE_QUOTE("trading.quote");

  @Getter
  private String value;
}

