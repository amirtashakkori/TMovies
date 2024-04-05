package com.example.tmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.resources.Compatibility;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.tmovies.Adapters.GenresAdapter;
import com.example.tmovies.Adapters.MoviesAdapter;
import com.example.tmovies.Api.ApiService;
import com.example.tmovies.Models.Movie;
import com.example.tmovies.Models.MovieList;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GenreListActivity extends AppCompatActivity implements MoviesAdapter.itemClickListener {

    RecyclerView genreRv;

    ApiService apiService;

    public void cast(){
        genreRv = findViewById(R.id.genreRv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_list);
        cast();
        apiService = new ApiService(this);

        int genre_id = getIntent().getIntExtra("genre_id" , -1);
        apiService.getGenreMovies(genre_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<MovieList>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(MovieList movieList) {
                genreRv.setLayoutManager(new LinearLayoutManager(GenreListActivity.this , LinearLayoutManager.VERTICAL , false));
                genreRv.setAdapter(new MoviesAdapter(GenreListActivity.this , movieList.getData() , GenreListActivity.this , false));
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    @Override
    public void itemClicked(long movie_id) {

    }

    @Override
    public void itemLongClicked(Movie movie) {

    }
}