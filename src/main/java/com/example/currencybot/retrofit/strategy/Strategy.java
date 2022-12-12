package com.example.currencybot.retrofit.strategy;


import java.math.BigDecimal;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Strategy {
    public static final String USD = "1";
    public static final String EUR = "2";
    public static final String PLD = "3";

    private final USDHandler usdHandler;
    private final EURHandler eurHandler;
    private final PLNHandler plnHandler;

    @Autowired
    public Strategy(USDHandler usdHandler, EURHandler eurHandler, PLNHandler plnHandler) {
        this.usdHandler = usdHandler;
        this.eurHandler = eurHandler;
        this.plnHandler = plnHandler;
    }

    public BigDecimal process(String currencyName) {
        switch (currencyName) {
            case USD:
                return usdHandler.getCurrency();
            case EUR:
                return eurHandler.getCurrency();
            case PLD:
                return plnHandler.getCurrency();
            default:
                throw new NoSuchElementException("�� ����� �� ��������� ������, ��������� ��");
        }
    }
}