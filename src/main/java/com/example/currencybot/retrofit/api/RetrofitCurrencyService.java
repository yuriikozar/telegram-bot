package com.example.currencybot.retrofit.api;

import com.example.currencybot.retrofit.model.Currency;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

@Service
public interface RetrofitCurrencyService {
    @GET("/NBUStatService/v1/statdirectory/exchange?json")
    Call<List<Currency>> getCurrencies();
}