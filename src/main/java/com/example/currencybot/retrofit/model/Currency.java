package com.example.currencybot.retrofit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@ToString
@Getter
public class Currency {
@JsonProperty("txt")
    String name;

@JsonProperty("rate")
    BigDecimal value;

@JsonProperty("exchangedate")
    String date;

}
