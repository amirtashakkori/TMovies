package com.example.tmovies.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tmovies.Api.ApiService;
import com.example.tmovies.Adapters.GenresAdapter;
import com.example.tmovies.GenreListActivity;
import com.example.tmovies.Models.Genre;
import com.example.tmovies.R;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GenreFragment extends Fragment {

    View view;
    RecyclerView genreRv;

    ApiService apiService;

    public void cast(){
        genreRv = view.findViewById(R.id.genreRv);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_genre, container, false);
        cast();

        apiService = new ApiService(getActivity());

        apiService.getGenreList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<Genre>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<Genre> genres) {
                genreRv.setLayoutManager(new GridLayoutManager(getActivity() , 3 , LinearLayoutManager.VERTICAL , false));
                genreRv.setAdapter(new GenresAdapter(getActivity(), genres, new GenresAdapter.itemClickListener() {
                    @Override
                    public void itemClicked(long genre_id) {
                        Intent intent = new Intent(getActivity() , GenreListActivity.class);
                        intent.putExtra("genre_id" , genre_id);
                        startActivity(intent);
                    }

                    @Override
                    public void itemLongClicked(Genre genre) {

                    }
                }));
            }

            @Override
            public void onError(Throwable e) {

            }
        });

        return view;
    }
}