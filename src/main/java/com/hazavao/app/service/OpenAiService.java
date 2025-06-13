package com.hazavao.app.service;

import io.github.cdimascio.dotenv.Dotenv;
import java.util.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAiService {

  private final String apiKey;

  public OpenAiService() {
    Dotenv dotenv = Dotenv.load();
    this.apiKey = dotenv.get("OPENAI_API_KEY");
  }

  private final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

  public String definirMotEnMalgache(String mot) {
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(apiKey);

    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("model", "gpt-3.5-turbo");

    List<Map<String, String>> messages = new ArrayList<>();
    messages.add(
        Map.of(
            "role",
            "user",
            "content",
            "Hazavao amin'ny teny malagasy ny dikan'ny teny hoe: " + mot));

    requestBody.put("messages", messages);

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

    ResponseEntity<Map> response = restTemplate.postForEntity(OPENAI_URL, entity, Map.class);

    List<Map<String, Object>> choices =
        (List<Map<String, Object>>) response.getBody().get("choices");
    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
    return (String) message.get("content");
  }
}
