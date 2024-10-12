package dev.johansf.movie_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OmdbApiConfig {
    @Value("${omdb.api.key}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }

    public String getApiUrl() {
        return "http://www.omdbapi.com/?apikey=" + apiKey + "&t=";
    }
}
