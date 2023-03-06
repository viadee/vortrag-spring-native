package de.viadee.parkhaus.adapter.gebuehrentabelle;

import java.math.BigDecimal;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "gebuehrentabelle")
public class GebuehrentabelleProperties {

  private BigDecimal preisEinzelkarteProStunde;
  private Duration toleranzEinzelkarteAusfahrtzeit;

  private BigDecimal preisMonatskarte;

  public BigDecimal getPreisEinzelkarteProStunde() {
    return preisEinzelkarteProStunde;
  }

  public void setPreisEinzelkarteProStunde(final BigDecimal preisEinzelkarteProStunde) {
    this.preisEinzelkarteProStunde = preisEinzelkarteProStunde;
  }

  public Duration getToleranzEinzelkarteAusfahrtzeit() {
    return toleranzEinzelkarteAusfahrtzeit;
  }

  public void setToleranzEinzelkarteAusfahrtzeit(final Duration toleranzEinzelkarteAusfahrtzeit) {
    this.toleranzEinzelkarteAusfahrtzeit = toleranzEinzelkarteAusfahrtzeit;
  }

  public BigDecimal getPreisMonatskarte() {
    return preisMonatskarte;
  }

  public void setPreisMonatskarte(final BigDecimal preisMonatskarte) {
    this.preisMonatskarte = preisMonatskarte;
  }
}
