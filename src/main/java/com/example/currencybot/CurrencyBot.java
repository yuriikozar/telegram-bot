package com.example.currencybot;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.example.currencybot.retrofit.strategy.Strategy;

@Component
public class CurrencyBot extends TelegramLongPollingBot {
  private static final String EUR = "Euro";
  private static final String USD = "USD";
  private static final String PLN = "PLN";
  private static final String START_COMMAND = "/start";
  private static Map<String, String> data;

  static {
    data = new HashMap<>();
  }

  private final Strategy strategy;

  private boolean isBotStarted = false;

  public CurrencyBot(Strategy strategy) {
    this.strategy = strategy;
  }

  @Value("${telegram.bot.username}")
  private String username;
  @Value("${telegram.bot.token}")
  private String token;


  @Override
  public String getBotUsername() {
    return username;
  }

  @Override
  public String getBotToken() {
    return token;
  }

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage()) {
      Message message = update.getMessage();
      if (!message.getText().isBlank()) {
        String text = message.getText();
        if (text.equals(START_COMMAND)) {
          chooseCurrencyModal(message);
        }

        if (isBotStarted && validateMessage(text)) {
          sendCurrency(text, message);
        }
        isBotStarted = true;
      }
    }
  }

  private boolean validateMessage(String text) {
    if (START_COMMAND.equals(text)) {
      return false;
    }
    return EUR.equals(text) || USD.equals(text) || PLN.equals(text);
  }
  private void chooseCurrencyModal(Message message) {
    ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
    ArrayList<KeyboardRow> keyboardRows = new ArrayList<KeyboardRow>();
    KeyboardRow keyboardButtons = new KeyboardRow();
    keyboardButtons.add(EUR);
    keyboardButtons.add(USD);
    keyboardButtons.add(PLN);
    keyboardRows.add(keyboardButtons);
    markup.setKeyboard(keyboardRows);
    markup.setResizeKeyboard(true);
//    markup.setOneTimeKeyboard(true);
    SendMessage sendMessage = new SendMessage();
    sendMessage.setText("Please, choose the currency");
    sendMessage.setChatId(String.valueOf(message.getChatId()));
    sendMessage.setReplyMarkup(markup);
    try {
      this.execute(sendMessage);
    } catch (TelegramApiException e) {
      throw new RuntimeException("Something went wrong with sending message");
    }
  }

  private void sendCurrency(String text, Message message) {
    SendMessage response = SendMessage.builder()
        .text(getExchangeRate(text).toString() + " Поточний курс " + message.getText())
        .chatId(message.getChatId())
        .build();
    try {
      execute(response);
    } catch (TelegramApiException e) {
      throw new RuntimeException("Something went wrong");
    }
  }

  private BigDecimal getExchangeRate(String text) {
    BigDecimal exchangeRate;
    switch (text) {
      case "Euro" -> {
        exchangeRate = strategy.process(Strategy.EUR);
        return exchangeRate;
      }
      case "USD" -> {
        exchangeRate = strategy.process(Strategy.USD);
        return exchangeRate;
      }
      case "PLN" -> {
        exchangeRate = strategy.process(Strategy.PLD);
        return exchangeRate;
      }
      default -> {
        exchangeRate = BigDecimal.ZERO;
        return exchangeRate;
      }
    }
  }
}
