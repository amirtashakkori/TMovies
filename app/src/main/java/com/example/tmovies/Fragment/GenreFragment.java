package com.example.tmovies.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

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

public class GenreFragment extends Fragment implements GenresAdapter.itemClickListener{

    View view;
    RecyclerView genreRv;
    LinearLayout anim_layout;
    NestedScrollView nested;

    ApiService apiService;
    Disposable genreDisposable;

    public void cast(){
        genreRv = view.findViewById(R.id.genreRv);
        nested = view.findViewById(R.id.nested);
        anim_layout = view.findViewById(R.id.anim_layout);
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
                genreDisposable = d;
            }

            @Override
            public void onSuccess(List<Genre> genres) {
                anim_layout.setVisibility(View.GONE);
                nested.setVisibility(View.VISIBLE);
                genreRv.setLayoutManager(new GridLayoutManager(getActivity() , 3 , LinearLayoutManager.VERTICAL , false));
                genreRv.setAdapter(new GenresAdapter(getActivity(), genres, GenreFragment.this));
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getActivity(), "An unknown error has occurred!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void itemClicked(String genre_name , long genre_id) {
        Intent intent = new Intent(getActivity() , GenreListActivity.class);
        intent.putExtra("genre_id" , genre_id);
        intent.putExtra("genre_name" , genre_name);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (genreDisposable != null)
            genreDisposable.dispose();
    }
}