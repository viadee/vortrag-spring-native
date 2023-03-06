package de.viadee.parkhaus.application.mapper;

import de.viadee.parkhaus.application.dto.ParkticketDTO;
import de.viadee.parkhaus.domain.model.Gebuehrentabelle;
import de.viadee.parkhaus.domain.model.Parkticket;
import org.springframework.stereotype.Component;

@Component
public class ParkticketDtoMapper {

  public ParkticketDTO map(
      Parkticket parkticket,
      Gebuehrentabelle gebuehrentabelle) {
    final ParkticketDTO result = new ParkticketDTO();

    result.setId(parkticket.getId());
    result.setEntered(parkticket.getEnteredTimestamp());
    result.setAllowedToExit(parkticket.isAllowedToExit(gebuehrentabelle));
    result.setPaymentDue(parkticket.calculatePaymentDue(gebuehrentabelle));
    result.setTicketTyp(parkticket.getTicketTyp());

    return result;
  }

}
