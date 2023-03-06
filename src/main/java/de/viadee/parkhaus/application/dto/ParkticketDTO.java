package de.viadee.parkhaus.application.dto;

import de.viadee.parkhaus.domain.model.TicketTyp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ParkticketDTO {

  private UUID id;
  private LocalDateTime entered;
  private boolean isAllowedToExit;
  private BigDecimal paymentDue;
  private TicketTyp ticketTyp;

  public TicketTyp getTicketTyp() {
    return ticketTyp;
  }

  public void setTicketTyp(final TicketTyp ticketTyp) {
    this.ticketTyp = ticketTyp;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public LocalDateTime getEntered() {
    return entered;
  }

  public void setEntered(LocalDateTime entered) {
    this.entered = entered;
  }

  public boolean isAllowedToExit() {
    return isAllowedToExit;
  }

  public void setAllowedToExit(boolean allowedToExit) {
    isAllowedToExit = allowedToExit;
  }

  public BigDecimal getPaymentDue() {
    return paymentDue;
  }

  public void setPaymentDue(BigDecimal paymentDue) {
    this.paymentDue = paymentDue;
  }
}
