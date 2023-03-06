package de.viadee.parkhaus.adapter.rest.parkticket;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.math.BigDecimal;
import java.util.UUID;

import de.viadee.parkhaus.application.dto.ParkticketDTO;
import de.viadee.parkhaus.application.services.EinfahrtService;
import de.viadee.parkhaus.application.services.ParkticketViewService;
import de.viadee.parkhaus.application.services.PaymentService;
import de.viadee.parkhaus.domain.model.AutoTyp;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parktickets")
@Validated
public class ParkticketRestController {

  private final EinfahrtService einfahrtService;
  private final ParkticketViewService parkticketViewService;
  private final PaymentService paymentService;

  public ParkticketRestController(
      final EinfahrtService einfahrtService,
      final ParkticketViewService parkticketViewService,
      final PaymentService paymentService) {
    this.einfahrtService = einfahrtService;
    this.parkticketViewService = parkticketViewService;
    this.paymentService = paymentService;
  }

  @PostMapping(produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public @Valid @NotNull ParkticketDTO postParktickets(
      @RequestParam("carType") final @NotNull AutoTyp autoTyp) {
    return einfahrtService.einfahrt(autoTyp);
  }

  @GetMapping(path = "/{id}",
      produces = APPLICATION_JSON_VALUE)
  public @Valid @NotNull ParkticketDTO getParkticket(@PathVariable("id") final @NotNull UUID id) {
    return parkticketViewService.view(id);
  }

  @PostMapping(path = "{id}/makePayment",
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE)
  public @Valid @NotNull PaymentAmountDTO makePayment(
      @RequestBody final @Valid @NotNull PaymentAmountDTO payment,
      @PathVariable("id") final @NotNull UUID id) {
    final BigDecimal returnAmount = paymentService.makePayment(id, payment.getAmount());
    return new PaymentAmountDTO(returnAmount);
  }

  @PostMapping(value = "{id}/convert",
      produces = APPLICATION_JSON_VALUE)
  public @Valid @NotNull ParkticketDTO convert(@PathVariable("id") final @NotNull UUID id) {
    return paymentService.convert(id);
  }
}
