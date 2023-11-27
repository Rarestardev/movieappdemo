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

public class ShowList_Movie extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    ImageView ic_search,ic_back;

    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_movie);

        init();
        onclick();
        processDate();

    }
    private void init(){
        ic_search = findViewById(R.id.ic_search);
        ic_back = findViewById(R.id.ic_back);

        layoutManager = new GridLayoutManager(ShowList_Movie.this,MainActivity.screenSize);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }
    private void onclick(){
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
    private void processDate(){
       Call<List<movie_model>> call = Retrofit_client.getInstance().getApi().getMovies();
       call.enqueue(new Callback<List<movie_model>>() {
           @Override
           public void onResponse(@NonNull Call<List<movie_model>> call, @NonNull Response<List<movie_model>> response) {
               List<movie_model> data = response.body();
               Movie_Adapter adapter = new Movie_Adapter(data,getApplicationContext());
               recyclerView.setAdapter(adapter);
           }
           @Override
           public void onFailure(@NonNull Call<List<movie_model>> call, @NonNull Throwable t) {
               Log.d("ListMovie","Error",t);
           }
       });

    }
}