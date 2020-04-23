package com.bux.tradebot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class Message {

    @NotNull
    @JsonProperty("t")
    String event;

    String id;
    @JsonProperty("v")
    int version;

    @JsonProperty("body")
    private Quote quote;

}
