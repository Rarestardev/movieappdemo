package com.rarestar.empirebesttv.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.rarestar.empirebesttv.Movie_model.movie_model;
import com.rarestar.empirebesttv.R;
import com.rarestar.empirebesttv.views.PlayMovieActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CardViewAdapterTrending extends RecyclerView.Adapter<CardViewAdapterTrending.MyViewHolder> {
    List<movie_model> List;
    Context context;
    public CardViewAdapterTrending(List<movie_model> List, Context context){
        this.List = List;
        this.context =context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item,parent,false);
        return new MyViewHolder(view);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(List.get(position).getMovie_name());
        holder.imdbRate.setText(List.get(position).getRate_imdb());
        Picasso.get().load(List.get(position).getPoster_link()).into(holder.imageSlide);
        /*if (position==List.size()-1){
            viewPager2.post(runnable);
        }*/
    }
    @Override
    public int getItemCount() {
        if(List==null) return 0;
        return List.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        CardView parent;
        ImageView imageSlide;
        TextView name,imdbRate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageSlide = itemView.findViewById(R.id.imageSlide);
            parent = itemView.findViewById(R.id.parent);
            name = itemView.findViewById(R.id.name);
            imdbRate = itemView.findViewById(R.id.imdbRate);
            parent.setOnClickListener(view -> {
                Intent intent = new Intent(context, PlayMovieActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("MovieName",List.get(getAdapterPosition()).getMovie_name());
                intent.putExtra("Year",List.get(getAdapterPosition()).getYear());
                intent.putExtra("Duration",List.get(getAdapterPosition()).getDuration());
                intent.putExtra("Genre",List.get(getAdapterPosition()).getGenre());
                intent.putExtra("Director",List.get(getAdapterPosition()).getDirector());
                intent.putExtra("Desc",List.get(getAdapterPosition()).getDesc());
                intent.putExtra("ImageLink",List.get(getAdapterPosition()).getPoster_link());
                intent.putExtra("Age",List.get(getAdapterPosition()).getAge());
                intent.putExtra("IMDB",List.get(getAdapterPosition()).getRate_imdb());
                intent.putExtra("Rate",List.get(getAdapterPosition()).getRate_user());
                intent.putExtra("Category",List.get(getAdapterPosition()).getCategory());
                intent.putExtra("Country",List.get(getAdapterPosition()).getCountry());
                intent.putExtra("Status",List.get(getAdapterPosition()).getStatus());
                context.startActivity(intent);
            });
        }
    }
    /*private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            List.addAll(List);
            notifyDataSetChanged();
        }
    };*/
}
