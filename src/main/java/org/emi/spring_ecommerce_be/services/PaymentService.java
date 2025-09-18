package org.emi.spring_ecommerce_be.services;

import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

  private final Logger log = LoggerFactory.getLogger(PaymentService.class);

  public boolean executePayment(BigDecimal total) {

    log.info("Trying to pay {} RON.", total);

    if (total.compareTo(BigDecimal.valueOf(50)) > 0) {
      log.info("Payment successful");
      return true;
    }

    log.info("Not enough money");
    return false;
  }
}
