package com.rarestar.empirebesttv.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestar.empirebesttv.Movie_model.comment_model;
import com.rarestar.empirebesttv.R;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.myViewHolder>{
    List<comment_model> data;
    Context context;
    View view;
    public CommentAdapter(List<comment_model> data, Context context) {
        this.data = data;
        this.context=context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_view,parent,false);
        return new myViewHolder(view);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.txt_username.setText(data.get(position).getUsername());
        holder.txt_textComment.setText(data.get(position).getText());
        holder.txt_date.setText(data.get(position).getDate());
        holder.txt_admin.setText("Admin :" + data.get(position).getAdmin());
        holder.txt_adminText.setText(data.get(position).getAdmin_text());

        if (holder.txt_admin.getText().toString().isEmpty() || holder.txt_adminText.getText().toString().isEmpty()){
            holder.admin.setVisibility(View.GONE);
            holder.line.setVisibility(View.GONE);
        }else {
            holder.admin.setVisibility(View.VISIBLE);
            holder.line.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public int getItemCount() {
        if(data==null) return 0;
        return data.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView txt_username,txt_date,txt_textComment,txt_admin,txt_adminText;
        View line;
        RelativeLayout admin;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_username = itemView.findViewById(R.id.txt_username);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_textComment = itemView.findViewById(R.id.txt_textComment);
            txt_admin = itemView.findViewById(R.id.txt_admin);
            txt_adminText = itemView.findViewById(R.id.txt_adminText);
            line = itemView.findViewById(R.id.line);
            admin = itemView.findViewById(R.id.admin);
        }
    }
}
