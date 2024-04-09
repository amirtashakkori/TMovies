package com.example.tmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tmovies.Api.ApiService;
import com.example.tmovies.Models.MovieDatails;
import com.example.tmovies.Adapters.ScreenShotsAdapter;
import com.squareup.picasso.Picasso;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieInfoActivity extends AppCompatActivity {

    ImageView backBtn ,  movieImg;
    TextView movieTv , rateTv , genresTv , reviewTv , txt_screenShots;
    TextView yearTv , directorTv , writerTv , actorsTv , awardsTv , releasedDateTv;
    RecyclerView screenShotsRv;
    NestedScrollView nested;
    LinearLayout anim_layout;

    ApiService apiService;
    Disposable disposable;

    public void cast(){
        nested = findViewById(R.id.nested);
        anim_layout = findViewById(R.id.anim_layout);

        backBtn = findViewById(R.id.backBtn);
        movieImg = findViewById(R.id.movieImg);

        movieTv = findViewById(R.id.movieTv);
        rateTv = findViewById(R.id.rateTv);
        genresTv = findViewById(R.id.genresTv);
        reviewTv = findViewById(R.id.reviewTv);

        yearTv = findViewById(R.id.yearTv);
        directorTv = findViewById(R.id.directorTv);
        writerTv = findViewById(R.id.writerTv);
        actorsTv = findViewById(R.id.actorsTv);
        awardsTv = findViewById(R.id.awardsTv);
        releasedDateTv = findViewById(R.id.releasedDateTv);
        txt_screenShots = findViewById(R.id.txt_screenShots);

        screenShotsRv = findViewById(R.id.screenShotsRv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        cast();
        apiService = new ApiService(this);

        long movieId = getIntent().getLongExtra("movie_id" , -1);
        apiService.getMovieDetails(movieId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<MovieDatails>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(MovieDatails movieDatails) {
                anim_layout.setVisibility(View.GONE);
                nested.setVisibility(View.VISIBLE);
                Picasso.get().load(movieDatails.getPoster()).into(movieImg);

                movieTv.setText(movieDatails.getTitle());
                rateTv.setText(movieDatails.getImdb_rating() + " (" + movieDatails.getImdb_votes() + ")");

                String genreList = movieDatails.getGenres() + "";
                String genres = genreList.substring(1 , genreList.length()-1);
                genresTv.setText(genres);

                reviewTv.setText(movieDatails.getPlot());
                yearTv.setText(movieDatails.getYear() + "");
                directorTv.setText(movieDatails.getDirector());
                writerTv.setText(movieDatails.getWriter());
                awardsTv.setText(movieDatails.getAwards());
                actorsTv.setText(movieDatails.getActors());
                releasedDateTv.setText(movieDatails.getReleased());

                if (movieDatails.getImages() != null){
                    screenShotsRv.setLayoutManager(new LinearLayoutManager(MovieInfoActivity.this , LinearLayoutManager.HORIZONTAL , false));
                    screenShotsRv.setAdapter(new ScreenShotsAdapter(MovieInfoActivity.this , movieDatails.getImages()));
                }
                else {
                    screenShotsRv.setVisibility(View.GONE);
                    txt_screenShots.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MovieInfoActivity.this, "An unknown error has occurred!", Toast.LENGTH_SHORT).show();
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
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null)
            disposable.dispose();
    }
}