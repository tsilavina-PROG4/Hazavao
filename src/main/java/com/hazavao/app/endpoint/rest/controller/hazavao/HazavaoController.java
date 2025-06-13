package com.hazavao.app.endpoint.rest.controller.hazavao;

import com.hazavao.app.service.OpenAiService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class HazavaoController {

  @Autowired private OpenAiService openAiService;

  @GetMapping("/hazavao")
  public String definirMot(@RequestParam String teny) {
    return openAiService.definirMotEnMalgache(teny);
  }
}
