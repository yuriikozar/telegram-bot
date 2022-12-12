package com.example.currencybot.retrofit.api;

import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
@RequiredArgsConstructor
public class RetrofitGenerator {
    private static final String BASE_URL = "https://bank.gov.ua";
    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    @Bean
    public static RetrofitCurrencyService createService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        return retrofit.create(RetrofitCurrencyService.class);
    }
}