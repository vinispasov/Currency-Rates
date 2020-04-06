package com.java.currencyrates;

import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

  private Logger logger = LoggerFactory.getLogger("CurrencyRatesService");

  @Autowired
  CurrencyRatesMockedRepository currencyRatesMockedRepository;

  List<CurrencyRateEntity> getAllCurrencyRates() {
    List<CurrencyRateEntity> allCurrencyRates = new ArrayList<>();
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

          if (currencyRatesMockedRepository.currencyExistInDatabase(currentCurrency)) {
            allCurrencyRates.add(new CurrencyRateEntity(currentCurrency, element.getAttribute("rate"), LocalDateTime.now()));
          }

        }
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return allCurrencyRates;
  }

  CurrencyRateEntity getCurrencyRateByCurrency(String wantedCurrency) {
    CurrencyRateEntity currencyRateEntity = new CurrencyRateEntity();
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

          wantedCurrency = wantedCurrency.toUpperCase();

          if (currentCurrency.equals(wantedCurrency)
              && currencyRatesMockedRepository.currencyExistInDatabase(currentCurrency)) {
            currencyRateEntity = new CurrencyRateEntity(currentCurrency, element.getAttribute("rate"), LocalDateTime.now());
          }
        }

      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return currencyRateEntity;
  }
}