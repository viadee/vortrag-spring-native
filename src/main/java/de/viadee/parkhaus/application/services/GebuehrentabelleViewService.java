package de.viadee.parkhaus.application.services;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import de.viadee.parkhaus.domain.model.Gebuehrentabelle;
import de.viadee.parkhaus.domain.services.GebuehrentabelleRepository;
import org.springframework.stereotype.Component;

@Component
public class GebuehrentabelleViewService {

  private final GebuehrentabelleRepository gebuehrentabelleRepository;

  public GebuehrentabelleViewService(
      final GebuehrentabelleRepository gebuehrentabelleRepository) {
    this.gebuehrentabelleRepository = gebuehrentabelleRepository;
  }

  public String viewAsText() {
    final Gebuehrentabelle gebuehrentabelle = gebuehrentabelleRepository.load();
    DecimalFormatSymbols formatDeDE = new DecimalFormatSymbols(Locale.GERMAN);
    final DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", formatDeDE);
    
    return "Gebührentabelle\n" +
           "===============\n" +
           "\n" +
           "Einfache Karte: " + decimalFormat.format(
        gebuehrentabelle.getPreisEinzelkarteProStunde())
           + " € pro angefangene Stunde\n" +
           "Monatskarte: " + decimalFormat.format(gebuehrentabelle.getPreisMonatskarte()) + " €\n" +
           "\n" +
           "(Nach dem Bezahlen muss bei einer einfachen Karte innerhalb von "
           + gebuehrentabelle.getToleranzEinzelkarteAusfahrtzeit()
                             .toMinutes() + " Minuten die Ausfahrt erfolgen.)";
  }

}
