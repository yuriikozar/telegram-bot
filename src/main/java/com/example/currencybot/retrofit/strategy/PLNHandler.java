package com.example.currencybot.retrofit.strategy;

import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.stereotype.Component;
import com.example.currencybot.retrofit.model.Currency;
import com.example.currencybot.retrofit.service.CurrencyService;

@Component
public class PLNHandler implements CurrencyHandler {
    private static final String currencyName = "Злотий";
    private final CurrencyService currencyService;

    public PLNHandler(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public BigDecimal getCurrency() {
        Optional<Currency> amount = currencyService.getCurrencies().stream().filter(c -> c.getName().equals(currencyName)).findFirst();
        return amount.orElseThrow().getValue();
    }
}