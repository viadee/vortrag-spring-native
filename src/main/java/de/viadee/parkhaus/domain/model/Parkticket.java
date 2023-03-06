package de.viadee.parkhaus.domain.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Inheritance
public abstract class Parkticket {

  @Id
  private UUID id;

  private LocalDateTime enteredTimestamp;

  private BigDecimal paymentDone;

  @Enumerated(EnumType.STRING)
  private AutoTyp cartype;

  protected Parkticket() {
  }

  public Parkticket(AutoTyp cartype, LocalDateTime enteredTimestamp) {
    this.id = UUID.randomUUID();
    this.enteredTimestamp = enteredTimestamp;
    this.cartype = cartype;
    this.paymentDone = BigDecimal.ZERO;
  }

  protected void setPaymentDone(BigDecimal paymentDone) {
    this.paymentDone = paymentDone;
  }

  public BigDecimal makePayment(final Gebuehrentabelle gebuehrentabelle,
                                final BigDecimal payment) {
    if (payment.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Payment <= 0");
    }

    final BigDecimal paymentDue = this.calculatePaymentDue(gebuehrentabelle);

    final BigDecimal returnAmount = payment.subtract(paymentDue)
                                           .max(BigDecimal.ZERO);
    final BigDecimal paymentAmount = payment.subtract(returnAmount)
                                            .max(BigDecimal.ZERO);

    this.paymentDone = this.paymentDone.add(paymentAmount);

    return returnAmount;
  }

  public BigDecimal calculatePaymentDue(final Gebuehrentabelle gebuehrentabelle) {
    final BigDecimal totalFee = this.calculateFee(gebuehrentabelle);

    return totalFee.subtract(getPaymentDone())
                   .max(BigDecimal.ZERO);
  }

  protected abstract BigDecimal calculateFee(Gebuehrentabelle gebuehrentabelle);

  public abstract boolean isAllowedToExit(Gebuehrentabelle gebuehrentabelle);

  public UUID getId() {
    return id;
  }

  public AutoTyp getCartype() {
    return cartype;
  }

  public BigDecimal getPaymentDone() {
    return paymentDone;
  }

  public LocalDateTime getEnteredTimestamp() {
    return enteredTimestamp;
  }

  public abstract TicketTyp getTicketTyp();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Parkticket that = (Parkticket) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
