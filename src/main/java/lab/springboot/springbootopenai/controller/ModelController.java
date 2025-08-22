package lab.springboot.springbootopenai.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Log4j2
@RequestMapping("/open-ai/eligible")
@RestController
public class ModelController {

    private final HttpClient httpClient;

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;
    @Value("${spring.ai.openai.projectId}")
    private String projectId;
    @Value("${spring.ai.openai.orgId}")
    private String orgId;

    public ModelController(HttpClient httpClient) {
        this.httpClient = httpClient;
    }


    @GetMapping("/models")
    public Object getAllModels() throws IOException, InterruptedException {
        log.debug("apiKey: {}, projectId: {},orgId: {}", apiKey, projectId, orgId);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/models"))
                .header("Authorization", "Bearer " + apiKey)
                .header("OpenAI-Organization", orgId)
                .header("OpenAI-Project", projectId)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        log.info("Status Code: " + response.statusCode());
        log.debug("Response Body: " + response.body());
        return response.body();

    }


}
