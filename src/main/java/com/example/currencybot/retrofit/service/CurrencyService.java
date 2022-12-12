package com.example.currencybot.retrofit.service;


import com.example.currencybot.retrofit.exception.RetrofitException;
import com.example.currencybot.retrofit.model.Currency;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.currencybot.retrofit.api.RetrofitCurrencyService;
import com.example.currencybot.retrofit.api.RetrofitGenerator;

@Service
public class CurrencyService {
    private final RetrofitCurrencyService retrofitCurrencyService;

    public CurrencyService() {
        this.retrofitCurrencyService = RetrofitGenerator.createService();
    }

    public List<Currency> getCurrencies() {
        try {
            return retrofitCurrencyService.getCurrencies().execute().body();
        } catch (IOException e) {
            throw new RetrofitException("Something went wrong while getting currencies from https/nbu");
        }
    }
}