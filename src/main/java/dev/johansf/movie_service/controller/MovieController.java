package dev.johansf.movie_service.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.johansf.movie_service.dto.MovieDTO;
import dev.johansf.movie_service.repository.MovieRepository;
import dev.johansf.movie_service.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;


    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/search")
    public MovieDTO searchMovieByTitle(@RequestParam String title) throws Exception {
        return movieService.fetchMovieByTitle(title);
    }

    @GetMapping("/external")
    public Object fetchMovieByTitle(@RequestParam String title) throws Exception {
        String apiurl = "http://www.omdbapi.com/?apikey=540f0468&t&t=";
        String url = apiurl + title;
        System.out.println(url);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ObjectNode> responseEntity = restTemplate.getForEntity(url, ObjectNode.class);
        ObjectNode jsonObject = responseEntity.getBody();

        return jsonObject;
    }
}
