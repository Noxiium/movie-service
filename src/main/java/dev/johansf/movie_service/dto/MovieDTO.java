package dev.johansf.movie_service.dto;

public class MovieDTO {
    private Integer id;
    private String title;
    private String director;
    private String genre;
    private int length;

    public MovieDTO(Integer id,String title, String director, String genre, int length) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.length = length;
    }

    public MovieDTO(String title, String director, String genre, int length) {
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.length = length;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public String getGenre() {
        return genre;
    }

    public int getLength() {
        return length;
    }
}
