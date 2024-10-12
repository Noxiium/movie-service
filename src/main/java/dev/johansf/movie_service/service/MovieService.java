package dev.johansf.movie_service.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.johansf.movie_service.config.OmdbApiConfig;
import dev.johansf.movie_service.dto.MovieDTO;
import dev.johansf.movie_service.entity.MovieEntity;
import dev.johansf.movie_service.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final OmdbApiConfig omdbApiConfig;

    @Autowired
    public MovieService(MovieRepository movieRepository, OmdbApiConfig omdbApiConfig) {
        this.movieRepository = movieRepository;
        this.omdbApiConfig = omdbApiConfig;
    }

    public MovieDTO fetchMovieByTitle(String title) throws Exception {
        MovieEntity movieEntity = movieRepository.findByTitle(title);

        if (movieEntity == null) {
            System.out.println("Movie not found in db");
            MovieEntity movieEntity1 = new MovieEntity();
            MovieDTO movieFetchedFromExternal = fetchMovieFromExternalAPI(title);
            //TODO check if new movie name is in database.
            movieEntity1.setTitle(movieFetchedFromExternal.getTitle());
            movieEntity1.setDirector(movieFetchedFromExternal.getDirector());
            movieEntity1.setGenre(movieFetchedFromExternal.getGenre());
            movieEntity1.setLength(movieFetchedFromExternal.getLength());
            movieEntity1 = movieRepository.save(movieEntity1);
            return new MovieDTO(movieEntity1.getId(), movieEntity1.getTitle(), movieEntity1.getDirector(), movieEntity1.getGenre(), movieEntity1.getLength());

        }
        return new MovieDTO(movieEntity.getId(),
                            movieEntity.getTitle(),
                            movieEntity.getDirector(),
                            movieEntity.getGenre(),
                            movieEntity.getLength());
    }

    private MovieDTO fetchMovieFromExternalAPI(String title) throws Exception {

        String apiUrl = omdbApiConfig.getApiUrl();
        String url = apiUrl + title;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ObjectNode> responseEntity = restTemplate.getForEntity(url, ObjectNode.class);
        if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
            //throw MovieNotFoundException
        }
        ObjectNode jsonObject = responseEntity.getBody();

        String movieTitle = jsonObject.has("Title") ? jsonObject.get("Title").asText() : "N/A";
        //TODO make sure that this title is the same as parameter title
        String director = jsonObject.has("Director") ? jsonObject.get("Director").asText() : "N/A";
        String genre = jsonObject.has("Genre") ? jsonObject.get("Genre").asText() : "N/A";
        int length = jsonObject.has("Runtime") ? parseRuntime(jsonObject.get("Runtime").asText()) : 0;

        return new MovieDTO(movieTitle, director, genre, length);
    }

    private int parseRuntime(String runtime) {
        return Integer.parseInt(runtime.replace(" min", "").trim());
    }
}
