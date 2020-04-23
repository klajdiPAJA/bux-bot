package com.bux.tradebot.rest;

import com.bux.tradebot.dto.ClosePositionResponse;
import com.bux.tradebot.dto.OpenPositionRequest;
import com.bux.tradebot.dto.OpenPositionResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Log4j2
@Component
public class TradeRestApi {
    private final RestTemplate restTemplate;
    private HttpHeaders httpHeaders;
    @Value("${bux.open.position.url}")
    private String openPositionUrl;
    @Value("${bux.close.position.url}")
    private String closePositionUrl;
    @Value("${bux.authorization.bearer}")
    private String authorizationBearer;

    TradeRestApi(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.httpHeaders = new HttpHeaders();

    }

    @PostConstruct
    private void updateHeaders() {
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE);
        httpHeaders.add(HttpHeaders.ACCEPT, MimeTypeUtils.APPLICATION_JSON_VALUE);
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + authorizationBearer);

    }

    public Optional<OpenPositionResponse> openPosition(final OpenPositionRequest openPositionRequest) {
        final HttpEntity<OpenPositionRequest> httpEntity = new HttpEntity<>(openPositionRequest,
                httpHeaders);
        try {
            final OpenPositionResponse successfulOpenPositionResponse = restTemplate
                    .postForObject(openPositionUrl, httpEntity, OpenPositionResponse.class);
            log.debug("Success open position for: {}", successfulOpenPositionResponse);
            return Optional.ofNullable(successfulOpenPositionResponse);
        } catch (final RestClientException e) {
            log.warn("Failed to open position with request {}", openPositionRequest, e);
            return Optional.empty();
        }
    }

    public boolean closePosition(final OpenPositionResponse openPositionProduct) {
        final HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);

        try {
            final ResponseEntity<ClosePositionResponse> responseEntity = restTemplate
                    .exchange(closePositionUrl, HttpMethod.DELETE, httpEntity,
                            ClosePositionResponse.class, openPositionProduct.getPositionId());
            log.debug("Success close position for: {}", responseEntity);
            return responseEntity.getStatusCode().is2xxSuccessful();
        } catch (final RestClientException e) {
            log.error("Failed to close position {} ", openPositionProduct, e);
            return false;
        }
    }
}
