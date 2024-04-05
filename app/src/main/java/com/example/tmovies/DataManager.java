package com.example.tmovies;

import com.example.tmovies.Api.ApiService;
import com.example.tmovies.Models.Movie;
import com.example.tmovies.Models.MovieList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class DataManager {

    ApiService apiService;

    public DataManager(ApiService apiService){
        this.apiService = apiService;
    }

    public Single<List<Movie>> fetchAllPagesData(int request) {

        List<Movie> allMovies = new ArrayList<>();
        List<Single<MovieList>> singles = new ArrayList<>();

        if (request == 0){
            for (int i = 1; i <= 5; i++) {
                singles.add(apiService.getMovieList(i));
            }
        } else if (request == 1) {
            for (int i = 6; i <= 10; i++) {
                singles.add(apiService.getMovieList(i));
            }
        } else if (request == 2) {
            for (int i = 11; i <= 15; i++) {
                singles.add(apiService.getMovieList(i));
            }
        } else if (request == 3) {
            for (int i = 16; i <= 20; i++) {
                singles.add(apiService.getMovieList(i));
            }
        } else if (request == 4) {
            for (int i = 21; i <= 25; i++) {
                singles.add(apiService.getMovieList(i));
            }
        }


        return Single.zip(singles, new Function<Object[], List<Movie>>() {
            @Override
            public List<Movie> apply(Object[] responses) throws Exception {
                for (Object response : responses) {
                    if (response instanceof MovieList) {
                        MovieList movieList = (MovieList) response;
                        allMovies.addAll(movieList.getData());
                    }
                }
                return allMovies;
            }
        });
    }
}

