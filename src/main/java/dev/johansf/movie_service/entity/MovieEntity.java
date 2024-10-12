package dev.johansf.movie_service.entity;

import jakarta.persistence.*;

@Entity
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String director;
    private String genre;
    private int length;

    public Integer getId() {
        return id;
    }

    public int getLength() {
        return length;
    }

    public String getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }

    public String getTitle() {
        return title;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
