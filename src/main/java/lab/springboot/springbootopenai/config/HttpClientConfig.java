package lab.springboot.springbootopenai.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class HttpClientConfig {

    @Bean
    public HttpClient getHttpClient() {
        return HttpClient.newHttpClient();
    }
}
