package de.viadee.parkhaus.application.services;

import java.time.LocalDateTime;

import de.viadee.parkhaus.application.dto.ParkticketDTO;
import de.viadee.parkhaus.application.mapper.ParkticketDtoMapper;
import de.viadee.parkhaus.domain.model.AutoTyp;
import de.viadee.parkhaus.domain.model.Einfachkarte;
import de.viadee.parkhaus.domain.model.Gebuehrentabelle;
import de.viadee.parkhaus.domain.services.GebuehrentabelleRepository;
import de.viadee.parkhaus.domain.services.ParkticketRepository;
import org.springframework.stereotype.Component;

@Component
public class EinfahrtService {

  private final ParkticketRepository parkticketRepository;
  private final GebuehrentabelleRepository gebuehrentabelleRepository;
  private final ParkticketDtoMapper mapper;

  public EinfahrtService(
      final ParkticketRepository parkticketRepository,
      final GebuehrentabelleRepository gebuehrentabelleRepository,
      final ParkticketDtoMapper mapper) {
    this.parkticketRepository = parkticketRepository;
    this.gebuehrentabelleRepository = gebuehrentabelleRepository;
    this.mapper = mapper;
  }

  public ParkticketDTO einfahrt(final AutoTyp autoTyp) {
    final Gebuehrentabelle gebuehrentabelle = gebuehrentabelleRepository.load();
    final Einfachkarte parkticket = new Einfachkarte(autoTyp,
                                                     LocalDateTime.now());

    return mapper.map(parkticketRepository.save(parkticket),
                      gebuehrentabelle);
  }

}
