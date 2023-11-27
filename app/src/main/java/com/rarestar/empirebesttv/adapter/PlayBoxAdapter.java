package com.rarestar.empirebesttv.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestar.empirebesttv.Movie_model.MovieDownloadAndPlay_model;
import com.rarestar.empirebesttv.R;
import com.rarestar.empirebesttv.video_player.VideoPlayer;

import java.util.List;

public class PlayBoxAdapter extends RecyclerView.Adapter<PlayBoxAdapter.myViewHolder>{
    List<MovieDownloadAndPlay_model> data;
    Context context;
    public static String LINK;
    public PlayBoxAdapter(List<MovieDownloadAndPlay_model> data, Context context) {
        this.data = data;
        this.context=context;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.box_play,parent,false);
        return new myViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        if (data.get(position).getQuality().equals("زیر نویس فارسی")){
            holder.parent.setVisibility(View.GONE);
        }else{
            holder.txt.setText("کیفیت  : " + data.get(position).getQuality());
        }
    }
    @Override
    public int getItemCount() {
        if(data==null) return 0;
        return data.size();
    }
    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView txt;
        CardView parent;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.txt);
            parent = itemView.findViewById(R.id.parent);
            parent.setOnClickListener(view -> {
                Intent PlayVideo = new Intent(context, VideoPlayer.class);
                PlayVideo.putExtra("Movie",data.get(getAdapterPosition()).getMovie_name());
                PlayVideo.putExtra("Quality",data.get(getAdapterPosition()).getQuality());
                LINK = data.get(getAdapterPosition()).getLink();
                context.startActivity(PlayVideo);
            });
        }
    }
}
