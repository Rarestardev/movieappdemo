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

public class ShowListAnimations extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView_animShow;
    ImageView ic_back,ic_search;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_animations);

        init();
        processDateAnimation();
        click();
    }
    private void init(){
        layoutManager = new GridLayoutManager(ShowListAnimations.this,MainActivity.screenSize);
        recyclerView_animShow = findViewById(R.id.recyclerView_animShow);
        recyclerView_animShow.setLayoutManager(layoutManager);

        ic_back = findViewById(R.id.ic_back);
        ic_search = findViewById(R.id.ic_search);
    }
    private void click(){
        ic_back.setOnClickListener(this);
        ic_search.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if (view==ic_back){
            finish();
        }
        if (view == ic_search){
            Intent searchActivity = new Intent(this,SearchActivity.class);
            startActivity(searchActivity);
        }
    }
    private void processDateAnimation(){
        Call<List<movie_model>> call = Retrofit_client.getInstance().getApi().getAnimation();
        call.enqueue(new Callback<List<movie_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<movie_model>> call, @NonNull Response<List<movie_model>> response) {
                List<movie_model> list = response.body();
                Movie_Adapter adapter = new Movie_Adapter(list,getApplicationContext());
                recyclerView_animShow.setAdapter(adapter);
            }
            @Override
            public void onFailure(@NonNull Call<List<movie_model>> call, @NonNull Throwable t) {
                Log.d("ShowListAnim","error",t);
            }
        });
    }


}