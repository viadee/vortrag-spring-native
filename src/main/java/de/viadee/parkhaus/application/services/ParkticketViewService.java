package de.viadee.parkhaus.application.services;

import java.util.NoSuchElementException;
import java.util.UUID;

import de.viadee.parkhaus.application.dto.ParkticketDTO;
import de.viadee.parkhaus.application.mapper.ParkticketDtoMapper;
import de.viadee.parkhaus.domain.services.GebuehrentabelleRepository;
import de.viadee.parkhaus.domain.services.ParkticketRepository;
import org.springframework.stereotype.Component;

@Component
public class ParkticketViewService {

  private final ParkticketRepository parkticketRepository;
  private final GebuehrentabelleRepository gebuehrentabelleRepository;
  private final ParkticketDtoMapper mapper;

  public ParkticketViewService(
      final ParkticketRepository parkticketRepository,
      final GebuehrentabelleRepository gebuehrentabelleRepository,
      final ParkticketDtoMapper mapper) {
    this.parkticketRepository = parkticketRepository;
    this.gebuehrentabelleRepository = gebuehrentabelleRepository;
    this.mapper = mapper;
  }

  public ParkticketDTO view(final UUID parktticketId) {
    return parkticketRepository
        .findById(parktticketId)
        .map(parkticket -> mapper.map(parkticket,
                                      gebuehrentabelleRepository.load()))
        .orElseThrow(NoSuchElementException::new);
  }

}
