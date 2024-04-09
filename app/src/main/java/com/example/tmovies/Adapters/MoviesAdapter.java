package com.example.tmovies.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tmovies.Models.Movie;
import com.example.tmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.item> {
    Context c;
    List<Movie> movies;
    itemClickListener listener;
    boolean imdbRank;

    public MoviesAdapter(Context c, List<Movie> movies, itemClickListener listener , boolean imdbRank) {
        this.c = c;
        this.movies = movies;
        this.listener = listener;
        this.imdbRank = imdbRank;
    }

    @NonNull
    @Override
    public item onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new item(LayoutInflater.from(c).inflate(R.layout.item_movie , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull item holder, int position) {
        holder.bindMovies(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class item extends RecyclerView.ViewHolder{
        ImageView movieImg;
        TextView movieTv , genresTv , imdbRatingTv;
        public item(@NonNull View itemView) {
            super(itemView);
            movieImg = itemView.findViewById(R.id.movieImg);
            movieTv = itemView.findViewById(R.id.movieTv);
            genresTv = itemView.findViewById(R.id.genresTv);
            imdbRatingTv = itemView.findViewById(R.id.imdbRatingTv);
        }

        public void bindMovies(Movie movie){
            Picasso.get().load(movie.getPoster()).into(movieImg);
            if (imdbRank)
                movieTv.setText(movie.getId() + "- " + movie.getTitle() + " " + movie.getYear());
            else
                movieTv.setText(movie.getTitle() + " " + movie.getYear());

            String genreList = movie.getGenres() + "";
            String genres = genreList.substring(1 , genreList.length()-1);
            genresTv.setText(genres);
            imdbRatingTv.setText(String.valueOf(movie.getImdb_rating()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.itemClicked(movie.getId());
                }
            });
        }
    }

    public interface itemClickListener{
        public void itemClicked(long movie_id);
    }
}
