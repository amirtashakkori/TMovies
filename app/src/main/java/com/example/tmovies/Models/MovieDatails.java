package com.example.tmovies.Models;

import java.util.List;

public class MovieDatails {
    int id , year ;
    String poster , title , plot , director , writer , actors , awards , released , imdb_votes;
    float imdb_rating;
    List<String> genres;
    public List<String> images;

    public int getId() {
        return id;
    }

    public String getImdb_votes() {
        return imdb_votes;
    }

    public int getYear() {
        return year;
    }

    public String getPoster() {
        return poster;
    }

    public String getTitle() {
        return title;
    }

    public String getPlot() {
        return plot;
    }

    public String getDirector() {
        return director;
    }

    public String getWriter() {
        return writer;
    }

    public String getActors() {
        return actors;
    }

    public String getAwards() {
        return awards;
    }

    public String getReleased() {
        return released;
    }

    public float getImdb_rating() {
        return imdb_rating;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<String> getImages() {
        return images;
    }
}
