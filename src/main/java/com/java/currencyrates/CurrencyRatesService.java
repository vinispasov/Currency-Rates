package com.java.currencyrates;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Service
public class CurrencyRatesService {

  private static final String ALL_CURRENCY = "All";
  private Logger logger = LoggerFactory.getLogger("CurrencyRatesService");

  @Autowired
  CurrencyRatesMockedRepository currencyRatesMockedRepository;

  Map<String, String> getAllCurrencyRates() {
    return findTheWantedCurrency(ALL_CURRENCY);
  }

  Map<String, String> getCurrencyRateByCurrency(String wantedCurrency) {
    return findTheWantedCurrency(wantedCurrency);
  }

  private Map<String, String> findTheWantedCurrency(String wantedCurrency) {

    Map<String, String> currencyRates = new HashMap<>();

    try (InputStream inputXml =
             new URL("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml?f58fbaa3ba2fa16c289dfdea766e83af").openConnection().getInputStream()) {

      DocumentBuilderFactory factory = DocumentBuilderFactory.
          newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(inputXml);


      NodeList nodeList = doc.getElementsByTagName("Cube");

      for (int i = 0; i < nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element element = (Element) node;
          String currentCurrency = element.getAttribute("currency");


          if (!wantedCurrency.equals(ALL_CURRENCY)) {
            wantedCurrency = wantedCurrency.toUpperCase();
            if (currentCurrency.equals(wantedCurrency)
                && currencyRatesMockedRepository.currencyExistInDatabase(currentCurrency)) {
              currencyRates.put(currentCurrency, element.getAttribute("rate"));
            }
          } else {
            if (currencyRatesMockedRepository.currencyExistInDatabase(currentCurrency)) {
              currencyRates.put(currentCurrency, element.getAttribute("rate"));
            }
          }

        }
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return currencyRates;
  }
}
