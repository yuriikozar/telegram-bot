package com.example.currencybot.retrofit.strategy;



import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.stereotype.Component;
import com.example.currencybot.retrofit.model.Currency;
import com.example.currencybot.retrofit.service.CurrencyService;

@Component
public class EURHandler implements CurrencyHandler {
    private static final String currencyName = "Євро";
    private final CurrencyService currencyService;

    public EURHandler(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public BigDecimal getCurrency() {
        Optional<Currency> amount = currencyService.getCurrencies().stream().filter(c -> c.getName().equals(currencyName)).findFirst();
        return amount.orElseThrow().getValue();
    }
}