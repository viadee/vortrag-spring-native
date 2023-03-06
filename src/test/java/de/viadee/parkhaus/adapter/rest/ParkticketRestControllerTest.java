package de.viadee.parkhaus.adapter.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import de.viadee.parkhaus.domain.model.AutoTyp;
import de.viadee.parkhaus.domain.model.Einfachkarte;
import de.viadee.parkhaus.domain.model.TicketTyp;
import de.viadee.parkhaus.domain.services.GebuehrentabelleRepository;
import de.viadee.parkhaus.domain.services.ParkticketRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ParkticketRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ParkticketRepository parkticketRepository;

  @Autowired
  private GebuehrentabelleRepository gebuehrentabelleRepository;

  @Test
  @DisplayName("POST /parktickets")
  void postParktickets() throws Exception {

    mockMvc.perform(post("/parktickets?carType=REGULAR"))
           .andExpect(status().isCreated())
           .andExpect(content().contentType(APPLICATION_JSON))
           .andExpect(jsonPath("$.paymentDue").value(0))
           .andExpect(jsonPath("$.allowedToExit").value(true))
           .andExpect(jsonPath("$.ticketTyp").value(TicketTyp.EINFACH.name()))
           .andDo(print());

  }

  @Test
  @DisplayName("GET /parktickets/{id}")
  @Transactional
  void getParkticket() throws Exception {
    Einfachkarte ticket = new Einfachkarte(AutoTyp.REGULAR,
                                           LocalDateTime.now()
                                                        .minusHours(3));
    ticket.makePayment(gebuehrentabelleRepository.load(),
                       BigDecimal.valueOf(3));
    ticket = parkticketRepository.save(ticket);

    mockMvc.perform(get("/parktickets/{id}",
                        ticket.getId()))
           .andExpect(status().isOk())
           .andExpect(content().contentType(APPLICATION_JSON))
           .andExpect(jsonPath("$.paymentDue").value(12))
           .andExpect(jsonPath("$.allowedToExit").value(false))
           .andExpect(jsonPath("$.ticketTyp").value(TicketTyp.EINFACH.name()))
           .andDo(print());

  }

  @Test
  @DisplayName("POST /parktickets/{id}/makePayment")
  @Transactional
  void postParkticketMakePayment() throws Exception {
    Einfachkarte ticket = new Einfachkarte(AutoTyp.REGULAR,
                                           LocalDateTime.now()
                                                        .minusHours(1));
    ticket = parkticketRepository.save(ticket);

    mockMvc.perform(post("/parktickets/{id}/makePayment",
                         ticket.getId())
                        .contentType(APPLICATION_JSON)
                        .content("{\n" +
                                 "   \"amount\": 10\n" +
                                 "}"))
           .andExpect(status().isOk())
           .andExpect(content().contentType(APPLICATION_JSON))
           .andExpect(jsonPath("$.amount").value(5))
           .andDo(print());

  }

  @Test
  @DisplayName("POST /parktickets/{id}/convert")
  @Transactional
  void postParktickeConvert() throws Exception {
    Einfachkarte ticket = new Einfachkarte(AutoTyp.REGULAR,
                                           LocalDateTime.now()
                                                        .minusHours(3));
    ticket.makePayment(gebuehrentabelleRepository.load(),
                       BigDecimal.valueOf(3));
    ticket = parkticketRepository.save(ticket);

    mockMvc.perform(post("/parktickets/{id}/convert",
                         ticket.getId()))
           .andExpect(status().isOk())
           .andExpect(content().contentType(APPLICATION_JSON))
           .andExpect(jsonPath("$.paymentDue").value(27))
           .andExpect(jsonPath("$.allowedToExit").value(false))
           .andExpect(jsonPath("$.ticketTyp").value(TicketTyp.MONATSKARTE.name()))
           .andDo(print());

  }
}
