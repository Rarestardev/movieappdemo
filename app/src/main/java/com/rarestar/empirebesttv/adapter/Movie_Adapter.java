package com.rarestar.empirebesttv.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestar.empirebesttv.Movie_model.movie_model;
import com.rarestar.empirebesttv.R;
import com.rarestar.empirebesttv.views.MainActivity;
import com.rarestar.empirebesttv.views.PlayMovieActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Movie_Adapter extends RecyclerView.Adapter<Movie_Adapter.myViewHolder>{
    List<movie_model> data;
    Context context;
    View view;
    public static String Movie;
    public static String Duration;
    private static final int ITEMS_PER_PAGE = 15;
    public Movie_Adapter(List<movie_model> data, Context context) {
        this.data = data;
        this.context=context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (MainActivity.largerScreen){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_larger,parent,false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
        }
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.textview_nameMovie.setText(data.get(position).getMovie_name());
        holder.textView_imdb.setText(data.get(position).getRate_imdb());
        Picasso.get().load(data.get(position).getPoster_link()).into(holder.imageView_cover);
        System.out.println(data.get(position).getPoster_link());
    }
    @Override
    public int getItemCount() {
        if(data==null) return 0;
        return Math.min(data.size(), ITEMS_PER_PAGE);
    }
    public class myViewHolder
            extends RecyclerView.ViewHolder{
        LinearLayout parent_cardView;
        ImageView imageView_cover;
        TextView textview_nameMovie,textView_imdb;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView_cover = itemView.findViewById(R.id.imageView_cover);
            textview_nameMovie = itemView.findViewById(R.id.textview_nameMovie);
            textView_imdb = itemView.findViewById(R.id.textView_imdb);

            parent_cardView = itemView.findViewById(R.id.parent_cardView);
            parent_cardView.setOnClickListener(view -> {
                Intent intent=new Intent(context , PlayMovieActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("MovieName",data.get(getAdapterPosition()).getMovie_name());
                intent.putExtra("Year",data.get(getAdapterPosition()).getYear());
                intent.putExtra("Duration",data.get(getAdapterPosition()).getDuration());
                intent.putExtra("Genre",data.get(getAdapterPosition()).getGenre());
                intent.putExtra("Director",data.get(getAdapterPosition()).getDirector());
                intent.putExtra("Desc",data.get(getAdapterPosition()).getDesc());
                intent.putExtra("ImageLink",data.get(getAdapterPosition()).getPoster_link());
                intent.putExtra("Age",data.get(getAdapterPosition()).getAge());
                intent.putExtra("IMDB",data.get(getAdapterPosition()).getRate_imdb());
                intent.putExtra("Rate",data.get(getAdapterPosition()).getRate_user());
                intent.putExtra("Category",data.get(getAdapterPosition()).getCategory());
                intent.putExtra("Country",data.get(getAdapterPosition()).getCountry());
                intent.putExtra("Status",data.get(getAdapterPosition()).getStatus());
                context.startActivity(intent);
                Movie = data.get(getAdapterPosition()).getMovie_name();
                Duration = data.get(getAdapterPosition()).getDuration();
            });
        }
    }
}
