package com.example.tmovies.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tmovies.Models.Genre;
import com.example.tmovies.R;

import java.util.List;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.item> {

    Context c;
    List<Genre> genres;
    itemClickListener listener;

    public GenresAdapter(Context c, List<Genre> genres, itemClickListener listener) {
        this.c = c;
        this.genres = genres;
        this.listener = listener;
    }

    @NonNull
    @Override
    public item onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new item(LayoutInflater.from(c).inflate(R.layout.item_genre , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull item holder, int position) {
        holder.bindGenres(genres.get(position));
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    public class item extends RecyclerView.ViewHolder{
        TextView genresTv;
        public item(@NonNull View itemView) {
            super(itemView);
            genresTv = itemView.findViewById(R.id.genresTv);
        }

        public void bindGenres(Genre genre){
            genresTv.setText(genre.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.itemClicked(genre.getName(), genre.getId());
                }
            });
        }

    }

    public interface itemClickListener{
        public void itemClicked(String genre_name , long genre_id);
    }

}
