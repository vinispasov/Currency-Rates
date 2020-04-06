package com.java.currencyrates;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyRatesController {
  @Autowired
  private CurrencyRatesService currencyRatesService;

  @GetMapping(value = "/allCurrencyRates")
  public List<CurrencyRateEntity> getAllCurrencyRates() {
    return currencyRatesService.getAllCurrencyRates();
  }

  @GetMapping(value = "/currency/{optionalCurrency}")
  public CurrencyRateEntity getCurrencyRateByCurrency(@PathVariable Optional<String> optionalCurrency) {
    if (optionalCurrency.isPresent()) {
      return currencyRatesService.getCurrencyRateByCurrency(optionalCurrency.get());
    }
    return new CurrencyRateEntity();
  }
}

