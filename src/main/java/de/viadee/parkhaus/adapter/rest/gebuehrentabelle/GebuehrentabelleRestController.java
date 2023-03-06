package de.viadee.parkhaus.adapter.rest.gebuehrentabelle;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

import de.viadee.parkhaus.application.services.GebuehrentabelleViewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gebuehrentabelle")
public class GebuehrentabelleRestController {

  private final GebuehrentabelleViewService gebuehrentabelleViewService;

  public GebuehrentabelleRestController(
      final GebuehrentabelleViewService gebuehrentabelleViewService) {
    this.gebuehrentabelleViewService = gebuehrentabelleViewService;
  }

  @GetMapping(produces = TEXT_PLAIN_VALUE)
  public String get() {
    return gebuehrentabelleViewService.viewAsText();
  }

}
