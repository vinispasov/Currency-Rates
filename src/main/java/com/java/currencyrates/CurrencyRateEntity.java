package com.java.currencyrates;

import java.time.LocalDateTime;

public class CurrencyRateEntity {

  private String currency;
  private String rate;
  private LocalDateTime localDateTime;

  CurrencyRateEntity() {

  }

  CurrencyRateEntity(String currency, String rate, LocalDateTime localDateTime) {
    this.currency = currency;
    this.rate = rate;
    this.localDateTime = localDateTime;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getRate() {
    return rate;
  }

  public void setRate(String rate) {
    this.rate = rate;
  }

  public LocalDateTime getLocalDateTime() {
    return localDateTime;
  }

  public void setLocalDateTime(LocalDateTime localDateTime) {
    this.localDateTime = localDateTime;
  }
}
