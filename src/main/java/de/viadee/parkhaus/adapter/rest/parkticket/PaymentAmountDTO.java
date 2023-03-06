package de.viadee.parkhaus.adapter.rest.parkticket;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class PaymentAmountDTO {

  private final BigDecimal amount;

  @JsonCreator
  public PaymentAmountDTO(@JsonProperty("amount") final BigDecimal amount) {
    this.amount = amount;
  }

  public BigDecimal getAmount() {
    return amount;
  }
}
