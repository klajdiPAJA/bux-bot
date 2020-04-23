package com.bux.tradebot;

import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * PicoCli Command class that will check if all args are passed to the command line
 */
@CommandLine.Command(name = "bot", mixinStandardHelpOptions = true, version = "1.0-SNAPSHOT",
        description = "Trade bot executable that opens/closes a trade position automatically based on user preferred configuration.")
public class ApplicationCommand implements Callable<Integer> {

    @CommandLine.Option(names = "--id", required = true, description = "@|bold The product ID you want to trade.|@")
    private String productId;

    @CommandLine.Option(names = {"--buyAt"}, required = true, description = "@|bold Target buy price for the product.|@")
    private Double buyPrice;

    @CommandLine.Option(names = {"--upperSell"}, required = true, description = "@|bold Upper limit sell price to close a position and make a profit.|@")
    private Double upperLimitSellPrice;

    @CommandLine.Option(names = {"--lowerSell"}, required = true, description = "@|bold Lower limit sell price to close a position at and make a loss.|@")
    private Double lowerLimitSellPrice;

    @Override
    public Integer call() {
        return 0;
    }
}