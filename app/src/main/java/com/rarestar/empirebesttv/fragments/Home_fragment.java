package com.rarestar.empirebesttv.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rarestar.empirebesttv.Movie_model.movie_model;
import com.rarestar.empirebesttv.R;
import com.rarestar.empirebesttv.adapter.CardViewAdapterLarger;
import com.rarestar.empirebesttv.adapter.CardViewAdapterTrending;
import com.rarestar.empirebesttv.adapter.Movie_Adapter;
import com.rarestar.empirebesttv.retrofit.Retrofit_client;
import com.rarestar.empirebesttv.views.MainActivity;
import com.rarestar.empirebesttv.views.ShowListAnimations;
import com.rarestar.empirebesttv.views.ShowListSerials;
import com.rarestar.empirebesttv.views.ShowListTrending;
import com.rarestar.empirebesttv.views.ShowListUserRate;
import com.rarestar.empirebesttv.views.ShowList_Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_fragment extends Fragment implements View.OnClickListener {
    RecyclerView recyclerView_movie, recyclerView_serial, recyclerView_animation,
            recyclerView_updateSerial,recyclerView_userRate,recyclerView_trending;
    RecyclerView viewpager2;
    TextView more1,more2,more3,more4,more5;
    Intent intent=new Intent();
    private final Handler slidesHandler = new Handler();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView_movie = rootView.findViewById(R.id.recyclerView_movie);
        recyclerView_serial = rootView.findViewById(R.id.recyclerView_serial);
        recyclerView_animation = rootView.findViewById(R.id.recyclerView_animation);
        recyclerView_updateSerial = rootView.findViewById(R.id.recyclerView_updateSerial);
        recyclerView_userRate = rootView.findViewById(R.id.recyclerView_userRate);
        recyclerView_trending = rootView.findViewById(R.id.recyclerView_trending);
        viewpager2 = rootView.findViewById(R.id.viewpager2);

        more1 = rootView.findViewById(R.id.more1);
        more2 = rootView.findViewById(R.id.more2);
        more3 = rootView.findViewById(R.id.more3);
        more4 = rootView.findViewById(R.id.more4);
        more5 = rootView.findViewById(R.id.more5);
        init();
        processDateMovie();
        processDateSerial();
        processDateAnimation();
        processDateTrending();
        DateUpdatedSerials();
        DataUserRate();
        return rootView;
    }
    private void init(){
        recyclerView_movie.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView_serial.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView_animation.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView_updateSerial.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView_userRate.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView_trending.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        //more text handle
        more1.setOnClickListener(this);
        more2.setOnClickListener(this);
        more3.setOnClickListener(this);
        more4.setOnClickListener(this);
        more5.setOnClickListener(this);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(10));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1- Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });

    }
    private void processDateTrending(){
        Call<List<movie_model>> call = Retrofit_client.getInstance().getApi().getTrending();
        call.enqueue(new Callback<List<movie_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<movie_model>> call, @NonNull Response<List<movie_model>> response) {
                List<movie_model> list = response.body();
                if (MainActivity.screenSize > 3){
                    viewpager2.setVisibility(View.GONE);
                    recyclerView_trending.setVisibility(View.VISIBLE);
                    CardViewAdapterLarger adapter = new CardViewAdapterLarger(list,getContext());
                    recyclerView_trending.setAdapter(adapter);
                }else{
                    recyclerView_trending.setVisibility(View.GONE);
                    viewpager2.setVisibility(View.VISIBLE);
                    CardViewAdapterTrending cardViewAdapterTrending = new CardViewAdapterTrending(list,getContext());
                    viewpager2.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                    viewpager2.setAdapter(cardViewAdapterTrending);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<movie_model>> call, @NonNull Throwable t) {
                Log.i("Trending","Trending",t);
            }
        });
    }
    private void processDateMovie(){
        Call<List<movie_model>> call = Retrofit_client.getInstance().getApi().getMovies();
        call.enqueue(new Callback<List<movie_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<movie_model>> call, @NonNull Response<List<movie_model>> response) {
                List<movie_model> list = response.body();
                Movie_Adapter adapter = new Movie_Adapter(list,getContext());
                recyclerView_movie.setAdapter(adapter);
            }
            @Override
            public void onFailure(@NonNull Call<List<movie_model>> call, @NonNull Throwable t) {
                Log.i("Movie","Movie",t);
            }
        });
    }
    private void processDateSerial(){
        Call<List<movie_model>> call = Retrofit_client.getInstance().getApi().getSerials();
        call.enqueue(new Callback<List<movie_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<movie_model>> call, @NonNull Response<List<movie_model>> response) {
                List<movie_model> list = response.body();
                Movie_Adapter adapter = new Movie_Adapter(list,getContext());
                recyclerView_serial.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<movie_model>> call, @NonNull Throwable t) {
                Log.i("Serial","Serial",t);
            }
        });
    }
    private void processDateAnimation(){
        Call<List<movie_model>> call = Retrofit_client.getInstance().getApi().getAnimation();
        call.enqueue(new Callback<List<movie_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<movie_model>> call, @NonNull Response<List<movie_model>> response) {
                List<movie_model> list = response.body();
                Movie_Adapter adapter = new Movie_Adapter(list,getContext());
                recyclerView_animation.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<movie_model>> call, @NonNull Throwable t) {
                Log.i("Animation","Anim",t);
            }
        });
    }
    private void DateUpdatedSerials(){
        Call<List<movie_model>> call = Retrofit_client.getInstance().getApi().getNewest();
        call.enqueue(new Callback<List<movie_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<movie_model>> call, @NonNull Response<List<movie_model>> response) {
                List<movie_model> list = response.body();
                Movie_Adapter adapter = new Movie_Adapter(list,getContext());
                recyclerView_updateSerial.setAdapter(adapter);
            }
            @Override
            public void onFailure(@NonNull Call<List<movie_model>> call, @NonNull Throwable t) {
                Log.i("UpdateSerial","Newest",t);
            }
        });
    }
    private void DataUserRate(){
        Call<List<movie_model>> call = Retrofit_client.getInstance().getApi().getUserRate();
        call.enqueue(new Callback<List<movie_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<movie_model>> call, @NonNull Response<List<movie_model>> response) {
                List<movie_model> list = response.body();
                Movie_Adapter adapter = new Movie_Adapter(list,getContext());
                recyclerView_userRate.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<movie_model>> call, @NonNull Throwable t) {
                Log.i("test","0",t);
            }
        });
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    @Override
    public void onClick(View view) {
        if (view == more1){
            Intent MovieActivity = new Intent(getContext(), ShowList_Movie.class);
            startActivity(MovieActivity);
        }
        if (view == more2){
            Intent SerialActivity = new Intent(getContext(), ShowListSerials.class);
            startActivity(SerialActivity);
        }
        if (view == more3){
            Intent AnimationActivity = new Intent(getContext(), ShowListAnimations.class);
            startActivity(AnimationActivity);
        }
        if (view == more4){
            Intent TrendingActivity = new Intent(getContext(), ShowListTrending.class);
            startActivity(TrendingActivity);
        }
        if (view == more5){
            Intent UserRateActivity = new Intent(getContext(), ShowListUserRate.class);
            startActivity(UserRateActivity);
        }
    }
}