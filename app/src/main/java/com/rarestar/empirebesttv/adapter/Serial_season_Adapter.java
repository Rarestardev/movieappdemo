package com.rarestar.empirebesttv.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestar.empirebesttv.Movie_model.serial_model;
import com.rarestar.empirebesttv.R;
import com.rarestar.empirebesttv.retrofit.Retrofit_client;
import com.rarestar.empirebesttv.views.PlayMovieActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Serial_season_Adapter extends RecyclerView.Adapter<Serial_season_Adapter.myViewHolder>{
    List<serial_model> data;
    List<serial_model> list;
    Context context;
    View view;
    public static String Serial_Season;
    public static Boolean isChecked = true;

    public Serial_season_Adapter(List<serial_model> data, Context context) {
        this.data = data;
        this.context=context;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.serial_list,parent,false);
        return new myViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        if (data.get(position).getSeason().equals("")){
            holder.cardView.setVisibility(View.GONE);
            holder.informationPlay.setVisibility(View.GONE);
            holder.recyclerView_episode.setVisibility(View.VISIBLE);
            isChecked = true;
        }else {
            holder.cardView.setVisibility(View.VISIBLE);
            holder.seasonView.setText(data.get(position).getSeason());
            holder.informationPlay.setVisibility(View.GONE);
            holder.recyclerView_episode.setVisibility(View.GONE);
            isChecked = false;
        }
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView seasonView;
        ScrollView informationPlay;
        CardView cardView;
        ImageView ImageView;
        RecyclerView recyclerView,recyclerView_episode;
        LinearLayout parent;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            seasonView = itemView.findViewById(R.id.seasonView);
            informationPlay = itemView.findViewById(R.id.informationPlay);
            cardView = itemView.findViewById(R.id.cardView);
            ImageView = itemView.findViewById(R.id.ImageView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            recyclerView_episode = itemView.findViewById(R.id.recyclerView_episode);
            parent = itemView.findViewById(R.id.parent);
            cardView.setOnClickListener(firstListener);
            recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
            recyclerView_episode.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
            Episode(PlayMovieActivity.Name);
        }
        View.OnClickListener firstListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Serial_Season = seasonView.getText().toString();
                EpisodeSerial(PlayMovieActivity.Name,Serial_Season);
                informationPlay.setVisibility(View.VISIBLE);
                informationPlay.focusableViewAvailable(seasonView);
                ImageView.setImageResource(R.drawable.down_24);
                cardView.setOnClickListener(secondListener);
            }
        };
        View.OnClickListener secondListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                informationPlay.setVisibility(View.GONE);
                informationPlay.focusableViewAvailable(seasonView);
                ImageView.setImageResource(R.drawable.left_24);
                cardView.setOnClickListener(firstListener);
            }
        };
        private void EpisodeSerial(String serial_name,String season) {
            Call<List<serial_model>> call = Retrofit_client.getInstance().getApi().EpisodeSerial(serial_name,season);
            call.enqueue(new Callback<List<serial_model>>() {
                @Override
                public void onResponse(@NonNull Call<List<serial_model>> call, @NonNull Response<List<serial_model>> response) {
                    list = response.body();
                    Serial_episode_Adapter adapter = new Serial_episode_Adapter(list,context);
                    recyclerView.setAdapter(adapter);
                }
                @Override
                public void onFailure(@NonNull Call<List<serial_model>> call, @NonNull Throwable t) {
                    Log.i("episode","ERR",t);
                }
            });
        }
        private void Episode(String serial_name) {
            Call<List<serial_model>> call = Retrofit_client.getInstance().getApi().Episode(serial_name);
            call.enqueue(new Callback<List<serial_model>>() {
                @Override
                public void onResponse(@NonNull Call<List<serial_model>> call, Response<List<serial_model>> response) {
                    List<serial_model> SerialList = response.body();
                    Serial_episode_Adapter adapter = new Serial_episode_Adapter(SerialList,context);
                    recyclerView_episode.setAdapter(adapter);
                }
                @Override
                public void onFailure(@NonNull Call<List<serial_model>> call, @NonNull Throwable t) {
                    Log.i("episode","ERR",t);
                }
            });
        }
    }
}
