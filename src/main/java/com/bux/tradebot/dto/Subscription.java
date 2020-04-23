package com.bux.tradebot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    Set<String> subscribeTo;
    Set<String> unsubscribeFrom;
}
