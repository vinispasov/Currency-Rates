package com.java.currencyrates;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(CurrencyRatesController.class)
@ContextConfiguration(classes = {CurrencyRatesController.class, CurrencyRatesService.class, CurrencyRatesMockedRepository.class})
class CurrencyRatesControllerTest {

  @Autowired
  private CurrencyRatesController currencyRatesController;

  @MockBean
  private CurrencyRatesService currencyRatesMockedService;

  private CurrencyRateEntity bgnCurrencyRate;
  private CurrencyRateEntity usdCurrencyRate;
  private CurrencyRateEntity zarCurrencyRate;

  @BeforeEach
  void setup() {
    LocalDateTime currentDateTime = LocalDateTime.now();
    bgnCurrencyRate = new CurrencyRateEntity("BGN", "1.9558", currentDateTime);
    usdCurrencyRate = new CurrencyRateEntity("USD", "1.0791", currentDateTime);
    zarCurrencyRate = new CurrencyRateEntity("ZAR", "20.3534", currentDateTime);
  }

  @Test
  void getAllCurrencyRatesTest() {
    //Arrange
    when(currencyRatesMockedService.getAllCurrencyRates()).thenReturn(Arrays.asList(bgnCurrencyRate, usdCurrencyRate, zarCurrencyRate));
    //Act
    List<CurrencyRateEntity> result = currencyRatesController.getAllCurrencyRates();
    //Assert
    assertEquals(result, Arrays.asList(bgnCurrencyRate, usdCurrencyRate, zarCurrencyRate));
  }

  @Test
  void getCurrencyRateByCurrencyTest() {
    //Arrange
    when(currencyRatesMockedService.getCurrencyRateByCurrency("BGN")).thenReturn(bgnCurrencyRate);
    //Act
    CurrencyRateEntity result = currencyRatesController.getCurrencyRateByCurrency(Optional.of("BGN"));
    //Assert
    assertEquals(result, bgnCurrencyRate);
  }
}
