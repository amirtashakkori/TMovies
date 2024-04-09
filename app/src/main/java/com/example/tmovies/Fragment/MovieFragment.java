package com.example.tmovies.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tmovies.Api.ApiService;
import com.example.tmovies.FullListActivity;
import com.example.tmovies.Adapters.MainSliderAdapter;
import com.example.tmovies.Adapters.MoviesAdapter;
import com.example.tmovies.Models.Movie;
import com.example.tmovies.Models.MovieList;
import com.example.tmovies.MovieInfoActivity;
import com.example.tmovies.R;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieFragment extends Fragment {
    
    View view;
    EditText searchEdt;
    TextView emptyStateTv;
    ViewPager2 mainSliderVp;
    RecyclerView top10Rv , searchListRv;
    LinearLayout  anim_layout , page , seeAllBtn , searchLayout , mainLayout , illLayout;

    ApiService apiService;
    MoviesAdapter moviesAdapter;
    Disposable searchDisposable , mainSliderDisposable , top20thDisposable;
    Handler mainSliderHandler = new Handler();

    public void cast(){
        searchEdt = view.findViewById(R.id.searchEdt);
        mainSliderVp = view.findViewById(R.id.mainSliderVp);
        top10Rv = view.findViewById(R.id.top10Rv);
        seeAllBtn = view.findViewById(R.id.seeAllBtn);
        anim_layout = view.findViewById(R.id.anim_layout);
        page = view.findViewById(R.id.page);
        emptyStateTv = view.findViewById(R.id.emptyStateTv);
        searchListRv = view.findViewById(R.id.searchListRv);
        searchLayout = view.findViewById(R.id.searchLayout);
        mainLayout = view.findViewById(R.id.mainLayout);
        illLayout = view.findViewById(R.id.illLayout);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie , container , false);
        cast();

        apiService = new ApiService(getActivity());

        setMainSlider(apiService);

        setTop20thRv(apiService);

        setButtons();

        return view;
    }
    
    public void setMainSlider(ApiService apiService){
        apiService.getMovieList(1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<MovieList>() {
            @Override
            public void onSubscribe(Disposable d) {
                mainSliderDisposable = d;
            }

            @Override
            public void onSuccess(MovieList movieList) {
                anim_layout.setVisibility(View.GONE);
                page.setVisibility(View.VISIBLE);

                mainSliderVp.setAdapter(new MainSliderAdapter(getActivity(), movieList.getData(), mainSliderVp, new MainSliderAdapter.itemClickListener() {
                    @Override
                    public void itemClicked(long movie_id) {
                        Intent intent = new Intent(getActivity() , MovieInfoActivity.class);
                        intent.putExtra("movie_id" , movie_id);
                        startActivity(intent);
                    }
                }));
                
                mainSliderVp.setClipToPadding(false);
                mainSliderVp.setClipChildren(false);
                mainSliderVp.setOffscreenPageLimit(3);
                mainSliderVp.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
                mainSliderVp.setCurrentItem(4);


                CompositePageTransformer transformer = new CompositePageTransformer();
//                transformer.addTransformer(new MarginPageTransformer(20));
                transformer.addTransformer(new ViewPager2.PageTransformer() {
                    @Override
                    public void transformPage(@NonNull View page, float position) {
                        float r = 1 - Math.abs(position);
                        page.setScaleY(0.85f + r * 0.15f);
                    }
                });

                Runnable sliderRunnable = new Runnable() {
                    @Override
                    public void run() {
                        mainSliderVp.setCurrentItem(mainSliderVp.getCurrentItem() + 1 , true);
                    }
                };

                mainSliderVp.setPageTransformer(transformer);
                mainSliderVp.post(sliderRunnable);

                mainSliderVp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        mainSliderHandler.removeCallbacks(sliderRunnable);
                        mainSliderHandler.postDelayed(sliderRunnable , 5000);
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getActivity(), "An unknown error has occurred!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    public void setTop20thRv(ApiService apiService){
        apiService.getMovieList(2).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MovieList>() {
            @Override
            public void onSubscribe(Disposable d) {
                top20thDisposable = d;
            }

            @Override
            public void onSuccess(MovieList movieList) {
                anim_layout.setVisibility(View.GONE);
                page.setVisibility(View.VISIBLE);

                top10Rv.setLayoutManager(new LinearLayoutManager(getActivity() , RecyclerView.VERTICAL , false));
                MoviesAdapter adapter = new MoviesAdapter(getActivity(), movieList.getData(), new MoviesAdapter.itemClickListener() {
                    @Override
                    public void itemClicked(long movie_id) {
                        Intent intent = new Intent(getActivity() , MovieInfoActivity.class);
                        intent.putExtra("movie_id" , movie_id);
                        startActivity(intent);
                    }

                }, false);
                top10Rv.setAdapter(adapter);
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getActivity(), "An unknown error has occurred!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setButtons(){
        searchEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    searchLayout.setVisibility(View.VISIBLE);
                    mainLayout.setVisibility(View.GONE);

                } else {
                    mainLayout.setVisibility(View.VISIBLE);
                    searchLayout.setVisibility(View.GONE);
                }
            }
        });

        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                illLayout.setVisibility(View.VISIBLE);
                searchListRv.setVisibility(View.GONE);
                String q = s.toString();
                if (!q.equals("")){
                    apiService.search(q).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<MovieList>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            searchDisposable = d;
                        }

                        @Override
                        public void onSuccess(MovieList movieList) {
                            if (movieList.getData().size() > 0){
                                illLayout.setVisibility(View.GONE);
                                emptyStateTv.setVisibility(View.GONE);
                                searchListRv.setVisibility(View.VISIBLE);
                                searchListRv.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL , false));
                                moviesAdapter = new MoviesAdapter(getActivity(), movieList.getData(), new MoviesAdapter.itemClickListener() {
                                    @Override
                                    public void itemClicked(long movie_id) {
                                        Intent intent = new Intent(getActivity() , MovieInfoActivity.class);
                                        intent.putExtra("movie_id" , movie_id);
                                        startActivity(intent);
                                    }

                                }, true);
                                searchListRv.setAdapter(moviesAdapter);
                            } else {
                                illLayout.setVisibility(View.VISIBLE);
                                searchListRv.setVisibility(View.GONE);
                                emptyStateTv.setVisibility(View.VISIBLE);
                                emptyStateTv.setText("There is no movie with this title");
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(getActivity(), "An unknown error has occurred!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    searchListRv.setVisibility(View.GONE);
                    illLayout.setVisibility(View.VISIBLE);
                    emptyStateTv.setVisibility(View.VISIBLE);
                    emptyStateTv.setText("Enter the desired title");
                }

                if (s.toString().equals("")){
                    illLayout.setVisibility(View.VISIBLE);
                    emptyStateTv.setText("Enter the desired title");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        seeAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , FullListActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onResume() {
        super.onResume();

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (searchEdt.hasFocus()) {
                    searchEdt.clearFocus();
                    searchEdt.setText("");
                    searchLayout.setVisibility(View.GONE);
                    mainLayout.setVisibility(View.VISIBLE);
                } else {
                    // Handle other back button press actions if needed
                    requireActivity().onBackPressed();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (searchDisposable != null)
            searchDisposable.dispose();

        if (mainSliderDisposable != null)
            mainSliderDisposable.dispose();

        if (top20thDisposable != null)
            top20thDisposable.dispose();
    }
}
