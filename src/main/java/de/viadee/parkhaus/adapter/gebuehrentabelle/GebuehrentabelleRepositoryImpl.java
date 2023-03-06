package de.viadee.parkhaus.adapter.gebuehrentabelle;

import de.viadee.parkhaus.domain.model.Gebuehrentabelle;
import de.viadee.parkhaus.domain.services.GebuehrentabelleRepository;
import org.springframework.stereotype.Component;

@Component
public class GebuehrentabelleRepositoryImpl implements GebuehrentabelleRepository {

  private final GebuehrentabelleProperties gebuehrentabelleProperties;

  public GebuehrentabelleRepositoryImpl(
      GebuehrentabelleProperties gebuehrentabelleProperties) {
    this.gebuehrentabelleProperties = gebuehrentabelleProperties;
  }

  @Override
  public Gebuehrentabelle load() {
    return new Gebuehrentabelle(gebuehrentabelleProperties.getPreisEinzelkarteProStunde(),
                                gebuehrentabelleProperties.getToleranzEinzelkarteAusfahrtzeit(),
                                gebuehrentabelleProperties.getPreisMonatskarte());
  }
}
