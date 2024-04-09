package com.example.tmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.resources.Compatibility;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    TextView genreNameTv;
    RecyclerView genreRv;
    LinearLayout anim_layout ;
    NestedScrollView listLay;
    ImageView backBtn;
    MoviesAdapter moviesAdapter ;

    ApiService apiService;
    Disposable disposable;

    long genre_id;
    String genre_name;

    public void cast(){
        genreRv = findViewById(R.id.genreRv);
        anim_layout = findViewById(R.id.anim_layout);
        listLay = findViewById(R.id.listLay);
        backBtn = findViewById(R.id.backBtn);
        genreNameTv = findViewById(R.id.genreNameTv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_list);
        cast();
        apiService = new ApiService(this);

        genre_id = getIntent().getLongExtra("genre_id" , -1);
        genre_name = getIntent().getStringExtra("genre_name");

        genreNameTv.setText(genre_name);

        apiService.getGenreMovies(genre_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MovieList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(MovieList movieList) {
                        anim_layout.setVisibility(View.GONE);
                        listLay.setVisibility(View.VISIBLE);
                        genreRv.setLayoutManager(new LinearLayoutManager(GenreListActivity.this , LinearLayoutManager.VERTICAL , false));
                        moviesAdapter = new MoviesAdapter(GenreListActivity.this , movieList.getData() , GenreListActivity.this , true);
                        genreRv.setAdapter(moviesAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(GenreListActivity.this, "An unknown error has occurred!", Toast.LENGTH_SHORT).show();
                    }
                });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void itemClicked(long movie_id) {
        Intent intent = new Intent(GenreListActivity.this , MovieInfoActivity.class);
        intent.putExtra("movie_id" , movie_id);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null)
            disposable.dispose();
    }
}