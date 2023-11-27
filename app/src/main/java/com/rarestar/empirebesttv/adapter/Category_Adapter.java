package com.rarestar.empirebesttv.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestar.empirebesttv.Movie_model.category_model;
import com.rarestar.empirebesttv.R;
import com.rarestar.empirebesttv.views.ShowGenre;

import java.util.List;

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.myViewHolder>{
    List<category_model> data;
    Context context;
    public static String GENRE;
    public Category_Adapter(List<category_model> data, Context context) {
        this.data=data;
        this.context=context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.textview_genre.setText(data.get(position).getCategory_genre());
    }

    @Override
    public int getItemCount() {
        if(data==null) return 0;
        return data.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout parent;
        TextView textview_genre;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            textview_genre = itemView.findViewById(R.id.textview_genre);
            parent = itemView.findViewById(R.id.parent);
            parent.setOnClickListener(view -> {
                Intent intent=new Intent(context, ShowGenre.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("genre",textview_genre.getText());
                GENRE = textview_genre.getText().toString();
                context.startActivity(intent);
            });
        }
    }
}
