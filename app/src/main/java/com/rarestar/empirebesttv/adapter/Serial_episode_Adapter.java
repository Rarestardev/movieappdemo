package com.rarestar.empirebesttv.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class Serial_episode_Adapter extends RecyclerView.Adapter<Serial_episode_Adapter.MyViewHolder> {
    List<serial_model> list;
    List<serial_model> data;
    Context context;
    public static String episode;
    public Serial_episode_Adapter(List<serial_model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.episode_link,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.season_episode_View.setText(list.get(position).getEpisode());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layout_quality;
        CardView cardView;
        TextView season_episode_View;
        ImageView ImageView;
        RecyclerView recyclerView,recyclerView_quality;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            season_episode_View = itemView.findViewById(R.id.season_episode_View);
            layout_quality = itemView.findViewById(R.id.layout_quality);
            cardView = itemView.findViewById(R.id.cardView);
            ImageView = itemView.findViewById(R.id.ImageView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            recyclerView_quality = itemView.findViewById(R.id.recyclerView_quality);

            recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
            recyclerView_quality.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
            if (Serial_season_Adapter.isChecked){
                cardView.setOnClickListener(thirdListener);
            }else {
                cardView.setOnClickListener(firstListener);
            }
        }
        View.OnClickListener firstListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_quality.setVisibility(View.VISIBLE);
                layout_quality.focusableViewAvailable(season_episode_View);
                ImageView.setImageResource(R.drawable.down_24);
                episode = season_episode_View.getText().toString();
                QualitySerial(PlayMovieActivity.Name,Serial_season_Adapter.Serial_Season,episode);
                cardView.setOnClickListener(secondListener);
            }
        };
        View.OnClickListener secondListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_quality.setVisibility(View.GONE);
                layout_quality.focusableViewAvailable(layout_quality);
                ImageView.setImageResource(R.drawable.left_24);
                cardView.setOnClickListener(firstListener);
            }
        };
        View.OnClickListener thirdListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView_quality.setVisibility(View.VISIBLE);
                recyclerView_quality.focusableViewAvailable(cardView);
                episode = season_episode_View.getText().toString();
                GetQualitySerial(PlayMovieActivity.Name,episode);
                ImageView.setImageResource(R.drawable.down_24);
                cardView.setOnClickListener(fourthListener);
            }
        };
        View.OnClickListener fourthListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView_quality.setVisibility(View.GONE);
                ImageView.setImageResource(R.drawable.left_24);
                cardView.setOnClickListener(thirdListener);
            }
        };
        private void QualitySerial(String serial_name,String season,String episode) {
            Call<List<serial_model>> call = Retrofit_client.getInstance().getApi().LinkSerial(serial_name,season,episode);
            call.enqueue(new Callback<List<serial_model>>() {
                @Override
                public void onResponse(@NonNull Call<List<serial_model>> call, @NonNull Response<List<serial_model>> response) {
                    data = response.body();
                    Serial_link_Adapter adapter = new Serial_link_Adapter(data,context);
                    recyclerView.setAdapter(adapter);
                }
                @Override
                public void onFailure(@NonNull Call<List<serial_model>> call, @NonNull Throwable t) {
                    Log.i("episode","ERR",t);
                }
            });
        }
        private void GetQualitySerial(String Serial_name,String episode) {
            Call<List<serial_model>> call = Retrofit_client.getInstance().getApi().QualitySerial(Serial_name,episode);
            call.enqueue(new Callback<List<serial_model>>() {
                @Override
                public void onResponse(@NonNull Call<List<serial_model>> call, @NonNull Response<List<serial_model>> response) {
                    List<serial_model> models = response.body();
                    Serial_link_Adapter adapter = new Serial_link_Adapter(models,context);
                    recyclerView_quality.setAdapter(adapter);
                }
                @Override
                public void onFailure(@NonNull Call<List<serial_model>> call, @NonNull Throwable t) {
                    Log.i("episode","ERR2",t);
                }
            });
        }
    }
}
