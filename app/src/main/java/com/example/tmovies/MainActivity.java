package com.example.tmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.tmovies.Fragment.GenreFragment;
import com.example.tmovies.Fragment.MovieFragment;
import com.example.tmovies.Fragment.ProfileFragment;
import com.example.tmovies.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottom_navigation_main;
    FrameLayout fragment_container;
    FragmentTransaction transaction;

    public void cast(){
        bottom_navigation_main = findViewById(R.id.bottom_navigation_main);
        fragment_container = findViewById(R.id.fragment_container);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cast();

        MovieFragment movieFragment = new MovieFragment();

        bottom_navigation_main.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                transaction = getSupportFragmentManager().beginTransaction();
                if (item.getItemId() == R.id.movieList){
                    transaction.replace(R.id.fragment_container , movieFragment);
                    transaction.addToBackStack("1");
                }
                else if (item.getItemId() == R.id.genreList){
                    transaction.replace(R.id.fragment_container , new GenreFragment());
                    transaction.addToBackStack("1");
                }
                else if (item.getItemId() == R.id.profile) {
                    transaction.replace(R.id.fragment_container , new ProfileFragment());
                    transaction.addToBackStack("1");
                }
                transaction.commit();
                return true;
            }
        });
    }
}