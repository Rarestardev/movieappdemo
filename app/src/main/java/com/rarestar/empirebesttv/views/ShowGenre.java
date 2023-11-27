package com.rarestar.empirebesttv.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.rarestar.empirebesttv.Movie_model.movie_model;
import com.rarestar.empirebesttv.R;
import com.rarestar.empirebesttv.adapter.Category_Adapter;
import com.rarestar.empirebesttv.adapter.Movie_Adapter;
import com.rarestar.empirebesttv.retrofit.Retrofit_client;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowGenre extends AppCompatActivity {
    TextView title;
    RecyclerView recyclerView;
    ImageView ic_back,ic_search;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_genre);
        title = findViewById(R.id.title);
        recyclerView = findViewById(R.id.recyclerView);
        ic_back = findViewById(R.id.ic_back);
        ic_search = findViewById(R.id.ic_search);
        layoutManager = new GridLayoutManager(ShowGenre.this,MainActivity.screenSize);
        recyclerView.setLayoutManager(layoutManager);
        ic_back.setOnClickListener(view -> finish());
        ic_search.setOnClickListener(view -> {
            Intent intent = new Intent(ShowGenre.this,SearchActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        String setTitle = Category_Adapter.GENRE;
        title.setText(setTitle);
        if (setTitle == null){
            get_Genre("");
        }else {
            get_Genre(setTitle);
        }
    }
    private void get_Genre(String key) {
        Call<List<movie_model>> call = Retrofit_client.getInstance().getApi().Genre(key);
        call.enqueue(new Callback<List<movie_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<movie_model>> call, @NonNull Response<List<movie_model>> response) {
                List<movie_model> list = response.body();
                Movie_Adapter adapter = new Movie_Adapter(list,ShowGenre.this);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(@NonNull Call<List<movie_model>> call, @NonNull Throwable t) {
                Log.d("response","ERROR",t);
            }
        });
    }
}