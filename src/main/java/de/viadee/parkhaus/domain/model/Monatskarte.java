package de.viadee.parkhaus.domain.model;

import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Monatskarte extends Parkticket {

  private LocalDateTime validUntil;

  protected Monatskarte() {
  }

  public Monatskarte(Einfachkarte einfachkarte) {
    super(einfachkarte.getCartype(),
          einfachkarte.getEnteredTimestamp());
    this.validUntil = einfachkarte.getEnteredTimestamp()
                                  .plusMonths(1);
    setPaymentDone(einfachkarte.getPaymentDone());
  }

  @Override
  protected BigDecimal calculateFee(final Gebuehrentabelle gebuehrentabelle) {
    return gebuehrentabelle.getPreisMonatskarte();
  }

  @Override
  public boolean isAllowedToExit(final Gebuehrentabelle gebuehrentabelle) {
    final LocalDateTime now = LocalDateTime.now();

    return getEnteredTimestamp().isBefore(now)
           && this.validUntil.isAfter(now)
           && calculatePaymentDue(gebuehrentabelle).compareTo(BigDecimal.ZERO) <= 0;
  }

  public LocalDateTime getValidUntil() {
    return validUntil;
  }

  public TicketTyp getTicketTyp() {
    return TicketTyp.MONATSKARTE;
  }

}
