package de.viadee.parkhaus.application.services;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.UUID;

import de.viadee.parkhaus.application.dto.ParkticketDTO;
import de.viadee.parkhaus.application.mapper.ParkticketDtoMapper;
import de.viadee.parkhaus.domain.model.Einfachkarte;
import de.viadee.parkhaus.domain.model.Gebuehrentabelle;
import de.viadee.parkhaus.domain.model.Monatskarte;
import de.viadee.parkhaus.domain.model.Parkticket;
import de.viadee.parkhaus.domain.services.GebuehrentabelleRepository;
import de.viadee.parkhaus.domain.services.ParkticketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class PaymentService {

  private final ParkticketRepository parkticketRepository;
  private final GebuehrentabelleRepository gebuehrentabelleRepository;
  private final ParkticketDtoMapper mapper;

  public PaymentService(
      ParkticketRepository parkticketRepository,
      GebuehrentabelleRepository gebuehrentabelleRepository,
      ParkticketDtoMapper mapper) {
    this.parkticketRepository = parkticketRepository;
    this.gebuehrentabelleRepository = gebuehrentabelleRepository;
    this.mapper = mapper;
  }

  public BigDecimal makePayment(UUID parkticketId, BigDecimal payment) {
    final Parkticket parkticket = parkticketRepository
        .findById(parkticketId)
        .orElseThrow(NoSuchElementException::new);

    final Gebuehrentabelle gebuehrentabelle = gebuehrentabelleRepository.load();
    return parkticket.makePayment(gebuehrentabelle,
                                  payment);
  }

  @Transactional
  public ParkticketDTO convert(UUID parkticketId) {
    final Parkticket parkticket = parkticketRepository
        .findById(parkticketId)
        .orElseThrow(NoSuchElementException::new);

    if (parkticket instanceof Einfachkarte) {
      final Einfachkarte einfachkarte = (Einfachkarte) parkticket;

      final Monatskarte monatskarte = parkticketRepository.save(new Monatskarte(einfachkarte));
      parkticketRepository.delete(einfachkarte);

      return mapper.map(monatskarte,
                        gebuehrentabelleRepository.load());
    } else {
      throw new IllegalStateException("Parkticket ist keine Einfachkarte");
    }
  }

}
