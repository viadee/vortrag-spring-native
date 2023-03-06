package de.viadee.parkhaus.domain.model;

import java.math.BigDecimal;
import java.time.Duration;

public class Gebuehrentabelle {

  private final BigDecimal preisEinzelkarteProStunde;
  private final Duration toleranzEinzelkarteAusfahrtzeit;

  private final BigDecimal preisMonatskarte;

  public Gebuehrentabelle(final BigDecimal preisEinzelkarteProStunde,
                          final Duration toleranzEinzelkarteAusfahrtzeit,
                          final BigDecimal preisMonatskarte) {
    this.preisEinzelkarteProStunde = preisEinzelkarteProStunde;
    this.toleranzEinzelkarteAusfahrtzeit = toleranzEinzelkarteAusfahrtzeit;
    this.preisMonatskarte = preisMonatskarte;
  }

  public BigDecimal getPreisMonatskarte() {
    return preisMonatskarte;
  }

  public BigDecimal getPreisEinzelkarteProStunde() {
    return preisEinzelkarteProStunde;
  }

  public Duration getToleranzEinzelkarteAusfahrtzeit() {
    return toleranzEinzelkarteAusfahrtzeit;
  }
}
