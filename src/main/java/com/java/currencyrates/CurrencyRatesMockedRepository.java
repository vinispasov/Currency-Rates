package com.java.currencyrates;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CurrencyRatesMockedRepository {
  private static final List<String> ALL_CURRENCY = Arrays.asList("USD", "JPY", "BGN", "CZK", "DKK", "GBP", "HUF", "PLN", "RON", "SEK", "CHF", "ISK", "NOK",
      "HRK", "RUB", "TRY", "AUD", "BRL", "CAD", "CNY", "HKD", "IDR", "ILS", "INR", "KRW", "MXN", "MYR", "NZD", "PHP", "SGD", "THB", "ZAR");

  boolean currencyExistInDatabase(String currency) {
    return ALL_CURRENCY.contains(currency);
  }

}
