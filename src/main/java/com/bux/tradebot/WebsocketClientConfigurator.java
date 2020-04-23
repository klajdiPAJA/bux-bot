package com.bux.tradebot;

import com.bux.tradebot.util.PropertyUtil;

import javax.websocket.ClientEndpointConfig;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class WebsocketClientConfigurator extends ClientEndpointConfig.Configurator {

    @Override
    public void beforeRequest(Map<String, List<String>> headers) {
        headers.put("Authorization", Collections.singletonList("Bearer " + PropertyUtil.read("bux.authorization.bearer")));
    }
}
