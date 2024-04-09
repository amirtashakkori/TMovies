package com.example.tmovies.Api;

import android.content.Context;

import com.example.tmovies.Models.Genre;
import com.example.tmovies.Models.MovieDatails;
import com.example.tmovies.Models.MovieList;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    RetrofitApiClass retrofitApiClass;
    public static final String BASE_URL = "https://moviesapi.ir/api/v1/";

    public ApiService(Context context){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        retrofitApiClass = retrofit.create(RetrofitApiClass.class);
    }

    public Single<MovieList> getMovieList(int page){
        return retrofitApiClass.getMovieList(page);
    }

    public Single<MovieList> search(String query){
        return retrofitApiClass.search(query);
    }

    public Single<MovieDatails> getMovieDetails(long id){
        return retrofitApiClass.getMovieDetail(id);
    }

    public Single<List<Genre>> getGenreList(){
        return retrofitApiClass.getGenreList();
    }

    public Single<MovieList> getGenreMovies(long id){
        return retrofitApiClass.getGenreMoviesResponse(id);
    }


}
