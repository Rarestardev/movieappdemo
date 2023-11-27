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

public class ShowListSerials extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView_SerialShow;

    ImageView ic_back,ic_search;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_serials);

        init();
        click();
        processDateSerial();
    }
    private void init(){
        layoutManager = new GridLayoutManager(ShowListSerials.this,MainActivity.screenSize);
        recyclerView_SerialShow = findViewById(R.id.recyclerView_serialShow);
        recyclerView_SerialShow.setLayoutManager(layoutManager);

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
    private void processDateSerial(){
        Call<List<movie_model>> call = Retrofit_client.getInstance().getApi().getSerials();
        call.enqueue(new Callback<List<movie_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<movie_model>> call, @NonNull Response<List<movie_model>> response) {
                List<movie_model> list = response.body();
                Movie_Adapter adapter = new Movie_Adapter(list,getApplicationContext());
                recyclerView_SerialShow.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<movie_model>> call, @NonNull Throwable t) {
                Log.d("ShowListSerial","error",t);
            }
        });
    }


}