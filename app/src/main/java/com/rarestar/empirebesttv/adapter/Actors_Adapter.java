package com.rarestar.empirebesttv.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestar.empirebesttv.Movie_model.movie_model;
import com.rarestar.empirebesttv.Movie_model.superstars_model;
import com.rarestar.empirebesttv.R;
import com.rarestar.empirebesttv.retrofit.Retrofit_client;
import com.rarestar.empirebesttv.views.PlayMovieActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Actors_Adapter extends RecyclerView.Adapter<Actors_Adapter.MyViewHolder>{
    List<superstars_model> List;
    Context context;
    public static boolean actors_adapter = false;
    public static String Movie,Desc,Category,Genre ,Director,
            Rate_imdb,Rate_user,
            Age,Year,Status,Poster_link,Country,Duration;

    public Actors_Adapter(java.util.List<superstars_model> list, Context context) {
        List = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_superstars_movie,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.movie_name.setText(List.get(position).getMovie_name() +
                " " + List.get(position).getYear());
        Picasso.get().load(List.get(position).getMovie_image()).into(holder.movie_image);
    }
    @Override
    public int getItemCount() {
        return List.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout parent;
        ImageView movie_image;
        TextView movie_name;
        String Name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            movie_image = itemView.findViewById(R.id.movie_image);
            movie_name = itemView.findViewById(R.id.movie_name);

            parent.setOnClickListener(view -> {
                Name = List.get(getAdapterPosition()).getMovie_name();
                searchMovieInDataBase(Name);
            });
        }
        private void searchMovieInDataBase(String key) {
            Call<List<movie_model>> call = Retrofit_client.getInstance().getApi().search(key);
            call.enqueue(new Callback<List<movie_model>>() {
                @Override
                public void onResponse(@NonNull Call<List<movie_model>> call, @NonNull Response<List<movie_model>> response) {
                    assert response.body() != null;
                    Movie = response.body().get(0).getMovie_name();
                    Year = response.body().get(0).getYear();
                    Duration = response.body().get(0).getDuration();
                    Genre = response.body().get(0).getGenre();
                    Director = response.body().get(0).getDirector();
                    Desc = response.body().get(0).getDesc();
                    Poster_link = response.body().get(0).getPoster_link();
                    Age = response.body().get(0).getAge();
                    Rate_imdb = response.body().get(0).getRate_imdb();
                    Rate_user = response.body().get(0).getRate_user();
                    Category = response.body().get(0).getCategory();
                    Country = response.body().get(0).getCountry();
                    Status = response.body().get(0).getStatus();
                    actors_adapter = true;
                    Intent intent = new Intent(context,PlayMovieActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                @Override
                public void onFailure(@NonNull Call<List<movie_model>> call, @NonNull Throwable t) {
                    Log.d("Actors Adapter","response failed",t);
                }
            });
        }
    }
}
