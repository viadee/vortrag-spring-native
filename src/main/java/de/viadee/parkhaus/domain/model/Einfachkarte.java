package de.viadee.parkhaus.domain.model;

import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Einfachkarte extends Parkticket {

  private LocalDateTime lastPaymentTimestamp;

  protected Einfachkarte() {

  }

  public Einfachkarte(AutoTyp autoTyp, LocalDateTime enteredTimestamp) {
    super(autoTyp, enteredTimestamp);
  }

  @Override
  protected BigDecimal calculateFee(final Gebuehrentabelle gebuehrentabelle) {
    final LocalDateTime from = getEnteredTimestamp();
    final LocalDateTime to = LocalDateTime.now();

    final Duration parkingDuration = Duration.between(from, to);

    // Sofortige Ausfahrt ist immer möglich
    if (parkingDuration.compareTo(gebuehrentabelle.getToleranzEinzelkarteAusfahrtzeit()) <= 0) {
      return BigDecimal.ZERO;
    }

    final BigDecimal parkingHoursToPay = BigDecimal.valueOf(
        Math.ceil(parkingDuration.toMinutes() / 60.0));

    return gebuehrentabelle.getPreisEinzelkarteProStunde()
                           .multiply(parkingHoursToPay)
                           .multiply(getDiscount());
  }

  private BigDecimal getDiscount() {
    BigDecimal discount = BigDecimal.ZERO;

    switch (getCartype()) {
      case SUV:
      case CONVERTIBLE:
      case REGULAR:
        discount = BigDecimal.valueOf(1.0);
        break;
      case VAN:
        discount = BigDecimal.valueOf(1.1);
        break;
      case MICRO:
        discount = BigDecimal.valueOf(0.9);
        break;
      case MOTORCYCLE:
        discount = BigDecimal.valueOf(0.5);
        break;
    }

    return discount;
  }

  @Override
  public BigDecimal makePayment(final Gebuehrentabelle gebuehrentabelle,
                                final BigDecimal payment) {
    final BigDecimal returnAmount = super.makePayment(gebuehrentabelle,
                                                      payment);
    this.lastPaymentTimestamp = LocalDateTime.now();
    return returnAmount;
  }

  @Override
  public boolean isAllowedToExit(final Gebuehrentabelle gebuehrentabelle) {

    if (calculatePaymentDue(gebuehrentabelle).compareTo(BigDecimal.ZERO) > 0) {
      return false;
    }

    final LocalDateTime now = LocalDateTime.now();
    final LocalDateTime ausfahrtMoeglichBis = Objects
        .requireNonNullElse(this.lastPaymentTimestamp,
                            getEnteredTimestamp())
        .plus(gebuehrentabelle.getToleranzEinzelkarteAusfahrtzeit());

    return getEnteredTimestamp().compareTo(now) <= 0
           && now.compareTo(ausfahrtMoeglichBis) <= 0;
  }

  public LocalDateTime getLastPaymentTimestamp() {
    return lastPaymentTimestamp;
  }

  public TicketTyp getTicketTyp() {
    return TicketTyp.EINFACH;
  }
}
