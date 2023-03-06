package de.viadee.parkhaus.adapter.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class GebuehrentabelleRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("GET /gebuehrentabelle")
  void getGebuehrentabelle() throws Exception {

    mockMvc.perform(get("/gebuehrentabelle"))
           .andExpect(status().isOk())
           .andExpect(content().string("Gebührentabelle\n" +
                                       "===============\n" +
                                       "\n" +
                                       "Einfache Karte: 5,00 € pro angefangene Stunde\n" +
                                       "Monatskarte: 30,00 €\n" +
                                       "\n" +
                                       "(Nach dem Bezahlen muss bei einer einfachen Karte innerhalb von 30 Minuten die Ausfahrt erfolgen.)"))
           .andDo(print());

  }

}
