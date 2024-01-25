package com.eteration.simplebanking.helper;

import java.util.Random;

public final class SimpleBankingHelper {

  private SimpleBankingHelper(){
  }

  public static String generateAccountNumber() {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    StringBuilder stringBuilder = new StringBuilder();
    Random random = new Random();
    while (stringBuilder.length() < 18) {
      int index = (int) (random.nextFloat() * characters.length());
      stringBuilder.append(characters.charAt(index));
    }
    return stringBuilder.toString();
  }
}
