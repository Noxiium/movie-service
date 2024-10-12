package dev.johansf.movie_service.repository;

import dev.johansf.movie_service.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {
    MovieEntity findMovieById(Integer id);
    MovieEntity findByTitle(String title);
}
