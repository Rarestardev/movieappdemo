package com.rarestar.empirebesttv.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.rarestar.empirebesttv.Movie_model.movie_model;
import com.rarestar.empirebesttv.R;
import com.rarestar.empirebesttv.adapter.Movie_Adapter;
import com.rarestar.empirebesttv.retrofit.Retrofit_client;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowListTrending extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView_trending;
    ImageView ic_search,ic_back;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_trending);

        init();
        processDateTrending();
        click();
    }
    private void init(){
        layoutManager = new GridLayoutManager(ShowListTrending.this,MainActivity.screenSize);
        recyclerView_trending = findViewById(R.id.recyclerView_trending);
        recyclerView_trending.setLayoutManager(layoutManager);

        ic_search = findViewById(R.id.ic_search);
        ic_back = findViewById(R.id.ic_back);
    }
    private void click(){
        ic_back.setOnClickListener(this);
        ic_search.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if (view == ic_back){
            finish();
        }
        if (view == ic_search){
            Intent intent = new Intent(this,SearchActivity.class);
            startActivity(intent);
        }
    }
    private void processDateTrending(){
        Call<List<movie_model>> call = Retrofit_client.getInstance().getApi().getNewest();
        call.enqueue(new Callback<List<movie_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<movie_model>> call, @NonNull Response<List<movie_model>> response) {
                List<movie_model> list = response.body();
                Movie_Adapter adapter = new Movie_Adapter(list,getApplicationContext());
                recyclerView_trending.setAdapter(adapter);
            }
            @Override
            public void onFailure(@NonNull Call<List<movie_model>> call, @NonNull Throwable t) {
                Log.d("ShowListTrend","error",t);
            }
        });
    }
}