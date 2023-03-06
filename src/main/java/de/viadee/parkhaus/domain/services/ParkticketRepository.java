package de.viadee.parkhaus.domain.services;

import de.viadee.parkhaus.domain.model.Parkticket;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ParkticketRepository extends JpaRepository<Parkticket, UUID> {

}
