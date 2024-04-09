package com.example.tmovies.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tmovies.Models.Movie;
import com.example.tmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainSliderAdapter extends RecyclerView.Adapter<MainSliderAdapter.item> {

    Context c;
    List<Movie> movies;
    itemClickListener listener;
    ViewPager2 viewPager2;

    public MainSliderAdapter(Context c, List<Movie> movies, ViewPager2 viewPager2 , itemClickListener listener) {
        this.c = c;
        this.movies = movies;
        this.viewPager2 = viewPager2;
        this.listener = listener;
    }

    @NonNull
    @Override
    public item onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.main_slider_item , parent , false);
        return new item(view);
    }

    @Override
    public void onBindViewHolder(@NonNull item holder, int position) {
        holder.bindMovies(movies.get(position));
        if (position == movies.size() -2 ){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class item extends RecyclerView.ViewHolder{
        ImageView movieImg;
        TextView movieTv , yearTv;
        public item(@NonNull View itemView) {
            super(itemView);
            movieImg = itemView.findViewById(R.id.movieImg);
            movieTv = itemView.findViewById(R.id.movieTv);
            yearTv = itemView.findViewById(R.id.yearTv);
        }

        public void bindMovies(Movie movie){
            Picasso.get().load(movie.getPoster()).into(movieImg);
            movieTv.setText(movie.getTitle());
            yearTv.setText(String.valueOf(movie.getYear()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.itemClicked(movie.getId());
                }
            });
        }

    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            movies.addAll(movies);
            notifyDataSetChanged();
        }
    };

    public interface itemClickListener{
        public void itemClicked(long movie_id);
    }
}
