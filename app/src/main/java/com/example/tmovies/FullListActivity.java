package com.example.tmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.tmovies.Api.ApiService;
import com.example.tmovies.Adapters.GenresAdapter;
import com.example.tmovies.Adapters.MoviesAdapter;
import com.example.tmovies.Models.Genre;
import com.example.tmovies.Models.Movie;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FullListActivity extends AppCompatActivity implements MoviesAdapter.itemClickListener , GenresAdapter.itemClickListener{

    RecyclerView Rv;
    Spinner movieRangeSpinner;
    ImageView backBtn;
    LinearLayout anim_layout;
    NestedScrollView nested;

    ApiService apiService;
    DataManager dataManager;
    List<Movie> movies;
    int selectedItem = 0;

    Disposable movie ;

    public void cast(){
        Rv = findViewById(R.id.Rv);
        movieRangeSpinner = findViewById(R.id.movieRangeSpinner);
        backBtn = findViewById(R.id.backBtn);
        anim_layout = findViewById(R.id.anim_layout);
        nested = findViewById(R.id.nested);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_list);
        cast();
        apiService = new ApiService(this);
        dataManager = new DataManager(apiService);
        movies = new ArrayList<>();
        setSpinner();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void setSpinner(){
        String[] genreTitles = new String[]{"Top 50", "50 to 100" , "100 to 150", "150 to 200", "200 to 250"};
        ArrayAdapter<String> periodSpinnerAdapter = new ArrayAdapter<String>(this , R.layout.item_movie_range_spinner , R.id.spinnerTv , genreTitles);
        periodSpinnerAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        movieRangeSpinner.setAdapter(periodSpinnerAdapter);
        movieRangeSpinner.setSelection(selectedItem);

        movieRangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                anim_layout.setVisibility(View.VISIBLE);
                nested.setVisibility(View.GONE);

                selectedItem = position;
                getMovies(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getMovies(int position){
        dataManager.fetchAllPagesData(selectedItem).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<Movie>>() {
            @Override
            public void onSubscribe(Disposable d) {
                movie = d;
            }

            @Override
            public void onSuccess(List<Movie> movies) {
                anim_layout.setVisibility(View.GONE);
                nested.setVisibility(View.VISIBLE);

                nested.scrollTo(0 , 0);
                Rv.setLayoutManager(new LinearLayoutManager(FullListActivity.this , LinearLayoutManager.VERTICAL , false));
                Rv.setAdapter(new MoviesAdapter(FullListActivity.this , movies , FullListActivity.this , true));
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    @Override
    public void itemClicked(long movie_id) {
        Intent intent = new Intent(FullListActivity.this , MovieInfoActivity.class);
        intent.putExtra("movie_id" , movie_id);
        startActivity(intent);
    }

    @Override
    public void itemLongClicked(Genre genre) {

    }

    @Override
    public void itemLongClicked(Movie movie) {

    }
}