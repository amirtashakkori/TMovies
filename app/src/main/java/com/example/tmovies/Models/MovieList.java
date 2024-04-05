package com.example.tmovies.Models;

import java.util.List;

public class MovieList {
    List<Movie> data;

    public MovieList(List<Movie> data) {
        this.data = data;
    }

    public List<Movie> getData() {
        return data;
    }

    public void setData(List<Movie> data) {
        this.data = data;
    }
}
