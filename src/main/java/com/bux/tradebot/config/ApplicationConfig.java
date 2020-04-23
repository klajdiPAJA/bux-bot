package com.bux.tradebot.config;

import com.bux.tradebot.WebsocketClient;
import com.bux.tradebot.dto.Wallet;
import com.bux.tradebot.service.MessageProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"com.bux.tradebot.service", "com.bux.tradebot.config", "com.bux.tradebot.rest"})
public class ApplicationConfig {

    @Bean
    public WebsocketClient webSocketTradingClient(
            final MessageProcessor messageProcessor) {
        return new WebsocketClient(messageProcessor);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public BotSettings botSettings(@Value("${id}") String id, @Value("${buyAt}") double buyAt, @Value("${upperSell}") double upperSellLimit, @Value("${lowerSell}") double lowerSellLimit) {
        if (!settingsValidation(buyAt, upperSellLimit, lowerSellLimit).isEmpty()) {
            throw new IllegalArgumentException("BuyAt price should be greater than lower limit selling price and smaller than upper limit selling price.");
        }
        return BotSettings.builder()
                .productId(id)
                .buyPrice(buyAt)
                .lowerLimitSellPrice(lowerSellLimit)
                .upperLimitSellPrice(upperSellLimit)
                .build();
    }

    @Bean
    public Wallet defaultInvestment(@Value("${bux.defaultInvestment.currency}") String currency, @Value("${bux.defaultInvestment.decimals}") int decimals, @Value("${bux.defaultInvestment.amount}") double amount) {
        return new Wallet(currency, decimals, amount);
    }

    private List<String> settingsValidation(double buyAt, double upperSellLimit, double lowerSellLimit) {
        List<String> errors = new ArrayList<>();
        if (buyAt == upperSellLimit) {
            errors.add("BuyAt price is equal to upperSell price!");
        }
        if (buyAt == lowerSellLimit) {
            errors.add("BuyAt price is equal to lowerSell price!");
        }
        if (buyAt > upperSellLimit) {
            errors.add("BuyAt price is greater than upperSell price, should be less than it!");
        }
        if (buyAt < lowerSellLimit) {
            errors.add("BuyAt price is less than lowerSell price, should be greater than it!");
        }
        return errors;
    }
}

