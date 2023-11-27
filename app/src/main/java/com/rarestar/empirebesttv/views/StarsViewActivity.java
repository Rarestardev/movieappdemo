package com.rarestar.empirebesttv.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.rarestar.empirebesttv.Movie_model.superstars_model;
import com.rarestar.empirebesttv.R;
import com.rarestar.empirebesttv.adapter.Actors_Adapter;
import com.rarestar.empirebesttv.retrofit.Retrofit_client;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StarsViewActivity extends AppCompatActivity {
    ImageView ic_back,image_actor;
    TextView textView_NameActor;
    RecyclerView recyclerView_works;
    String NameActor,Image_Superstars;
    RecyclerView.LayoutManager layoutManager;
    List<superstars_model> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stars_view);

        init();
    }
    private void init() {
        image_actor = findViewById(R.id.image_actor);

        ic_back = findViewById(R.id.ic_back);
        ic_back.setOnClickListener(view -> finish());

        NameActor = getIntent().getStringExtra("Superstars");
        Image_Superstars = getIntent().getStringExtra("Image_Superstars");
        Picasso.get().load(Image_Superstars).into(image_actor);
        textView_NameActor = findViewById(R.id.textView_NameActor);
        textView_NameActor.setText(NameActor);

        recyclerView_works = findViewById(R.id.recyclerView_works);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView_works.setLayoutManager(layoutManager);
        Stars_movie(NameActor);
    }
    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }
    private void Stars_movie(String superstar_name){
        Call<List<superstars_model>> call = Retrofit_client.getInstance().getApi().superstar_movie(superstar_name);
        call.enqueue(new Callback<List<superstars_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<superstars_model>> call, @NonNull Response<List<superstars_model>> response) {
                list = response.body();
                Actors_Adapter adapter = new Actors_Adapter(list,StarsViewActivity.this);
                recyclerView_works.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<superstars_model>> call, @NonNull Throwable t) {
                Log.d("StarsView","Response Failed",t);
            }
        });
    }
}