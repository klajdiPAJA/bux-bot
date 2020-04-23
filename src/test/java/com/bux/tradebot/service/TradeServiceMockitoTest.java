package com.bux.tradebot.service;

import com.bux.tradebot.config.ApplicationConfig;
import com.bux.tradebot.dto.OpenPositionResponse;
import com.bux.tradebot.dto.Product;
import com.bux.tradebot.dto.Quote;
import com.bux.tradebot.rest.TradeRestApi;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TradeServiceMockitoTest extends BaseMockitoTest {

    TradeService tradeService;
    @Mock
    TradeRestApi restApi;
    //private Product openPositionProduct = new Product(defaultProductId, "sy", "Sy");
    OpenPositionResponse openPositionResponse;

    @Before
    public void setup() {
        openPositionResponse = new OpenPositionResponse();
        openPositionResponse.setPositionId("p");
        ApplicationConfig config = new ApplicationConfig();
        tradeService = new TradeService(defaultSettings, restApi, config.defaultInvestment("BUX", 2, 10.00));
    }

    @Test
    public void processQuote_QuotePriceBetweenLimitsAndNotBuyingPrice_ShouldIgnoreQuote() throws IOException {
        //given
        Quote quote = new Quote(defaultProductId, 15.00);
        //when
        tradeService.processQuote(quote);
        //then
        verify(restApi, times(0)).openPosition(any());
        verify(restApi, times(0)).closePosition(any());
    }

    @Test
    public void processQuote_ProductSecurityIdNotInSubscribedProductIds_ShouldIgnoreQuote() throws IOException {
        //given
        Quote quote = new Quote("NaN", 20.00);
        //when
        tradeService.processQuote(quote);
        //then
        verify(restApi, times(0)).openPosition(any());
        verify(restApi, times(0)).closePosition(any());
    }

    @Test
    public void processQuote_QuotePriceEqualToBuyingPrice_ShouldOpenPosition() throws IOException {
        //given
        Quote quote = new Quote(defaultProductId, defaultBuyPrice);
//        openPositionResponse.setProduct(openPositionProduct);
        when(restApi.openPosition(any())).thenReturn(Optional.of(openPositionResponse));
        //when
        tradeService.processQuote(quote);
        //then
        assertEquals(openPositionResponse, tradeService.getOpenPosition());
        verify(restApi, times(1)).openPosition(any());

    }

    @Test
    public void processQuote_QuotePriceReachedLowerLimitSellPrice_ShouldClosePosition() throws IOException {
        //given
        //Open a position to later close it
        openANewPosition();
        when(restApi.closePosition(openPositionResponse)).thenReturn(true);
        Quote closePositionQuote = new Quote(defaultProductId, 0.00);
        //when
        tradeService.processQuote(closePositionQuote);
        //then
        assertNull(tradeService.getOpenPosition());
        verify(restApi, times(1)).closePosition(any());

    }

    @Test
    public void processQuote_QuotePriceReachedUpperLimitSellPrice_ShouldClosePosition() throws IOException {
        //given
        openANewPosition();
        when(restApi.closePosition(openPositionResponse)).thenReturn(true);
        Quote closePositionQuote = new Quote(defaultProductId, 101.00);
        //when
        tradeService.processQuote(closePositionQuote);
        //then
        assertNull(tradeService.getOpenPosition());
        verify(restApi, times(1)).closePosition(any());

    }

    private void openANewPosition() {
        Quote openPositionQuote = new Quote(defaultProductId, defaultBuyPrice);
        when(restApi.openPosition(any())).thenReturn(Optional.of(openPositionResponse));
        tradeService.processQuote(openPositionQuote);
        verify(restApi, times(1)).openPosition(any());
    }

}
