package com.example.tmovies.Api;


import com.example.tmovies.Models.Genre;
import com.example.tmovies.Models.MovieDatails;
import com.example.tmovies.Models.MovieList;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitApiClass {
    @Headers("Cache-Control: max-age=3600")
    @GET("movies")
    Single<MovieList> getMovieList(@Query("page") int page);

    @GET("movies")
    Single<MovieList> search(@Query("q") String query);

    @GET("movies/{movie_id}")
    Single<MovieDatails> getMovieDetail(@Path("movie_id") long id);

    @GET("genres")
    Single<List<Genre>> getGenreList();

    @GET("genres/{genre_id}/movies")
    Single<MovieList> getGenreMoviesResponse(@Path("genre_id") long genre_id);

}
