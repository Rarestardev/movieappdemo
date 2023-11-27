package com.rarestar.empirebesttv.adapter;

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

import com.rarestar.empirebesttv.Movie_model.superstars_model;
import com.rarestar.empirebesttv.R;
import com.rarestar.empirebesttv.views.StarsViewActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Superstars_Adapter extends RecyclerView.Adapter<Superstars_Adapter.myViewHolder>{
    List<superstars_model> data;
    Context context;
    View view;
    public Superstars_Adapter(List<superstars_model> data, Context context) {
        this.context=context;
        this.data = data;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.actors_card_view,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.name_actors.setText(data.get(position).getSuperstar_name());
        Picasso.get().load(data.get(position).getSuperstar_imageLink()).into(holder.actors_image);
    }
    @Override
    public int getItemCount() {
        if(data==null) return 0;
        return data.size();
    }
    public class myViewHolder extends RecyclerView.ViewHolder{
        CardView cardView_actors;
        TextView name_actors;
        ImageView actors_image;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView_actors = itemView.findViewById(R.id.cardView_actors);
            name_actors = itemView.findViewById(R.id.name_actors);
            actors_image = itemView.findViewById(R.id.actors_image);
            cardView_actors.setOnClickListener(view -> {
                Intent intent = new Intent(context, StarsViewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Superstars",data.get(getAdapterPosition()).getSuperstar_name());
                intent.putExtra("Image_Superstars",data.get(getAdapterPosition()).getSuperstar_imageLink());
                context.startActivity(intent);
            });
        }
    }
}
