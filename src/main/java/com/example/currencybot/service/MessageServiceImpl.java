package com.example.currencybot.service;

import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
  @Override
  public void sendMessage() {
    System.out.println("Message sent");
  }
}
