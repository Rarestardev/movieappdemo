package com.rarestar.empirebesttv.adapter;

import static android.content.Context.MODE_PRIVATE;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestar.empirebesttv.Movie_model.user_model;
import com.rarestar.empirebesttv.R;
import com.rarestar.empirebesttv.fragments.Saved_fragment;
import com.rarestar.empirebesttv.retrofit.Retrofit_client;
import com.rarestar.empirebesttv.views.MainActivity;
import com.rarestar.empirebesttv.views.PlayMovieActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveMovieAdapter extends RecyclerView.Adapter<SaveMovieAdapter.myViewHolder>{
    List<user_model> data;
    Context context;
    View view;
    public static String Movie;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "myPref";
    private static final String KEY_NAME = "name";
    public static String Duration;
    public SaveMovieAdapter(List<user_model> data, Context context) {
        this.data = data;
        this.context=context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_save_fragment,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.textview_nameMovie.setText(data.get(position).getMovie_name());
        holder.textView_imdb.setText(data.get(position).getRate_imdb());
        Picasso.get().load(data.get(position).getPoster_link()).into(holder.imageView_cover);
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        LinearLayout parent_cardView;
        ImageView imageView_cover,delete;
        TextView textview_nameMovie,textView_imdb;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_cover = itemView.findViewById(R.id.imageView_cover);
            textview_nameMovie = itemView.findViewById(R.id.textview_nameMovie);
            textView_imdb = itemView.findViewById(R.id.textView_imdb);
            delete = itemView.findViewById(R.id.delete);

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
                intent.putExtra("Status",data.get(getAdapterPosition()).getStatus());
                context.startActivity(intent);
                Movie = data.get(getAdapterPosition()).getMovie_name();
                Duration = data.get(getAdapterPosition()).getDuration();
            });
            delete.setOnClickListener(view -> {
                //check save movie in database
                sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
                String name = sharedPreferences.getString(KEY_NAME,null);
                if (name != null){
                    DeleteSaveMovieInDataBase(name,data.get(getAdapterPosition()).getMovie_name());
                }
            });
        }
    }
    private void DeleteSaveMovieInDataBase(String username,String movie) {
        Call<List<user_model>> call = Retrofit_client.getInstance().getApi().DeleteSaveMovie(username,movie);
        call.enqueue(new Callback<List<user_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<user_model>> call, @NonNull Response<List<user_model>> response) {
                assert response.body() != null;
                if (response.body().get(0).getMessage().equals("Success")){
                    Toast.makeText(context, "حذف شد!", Toast.LENGTH_SHORT).show();
                    MainActivity activity = new MainActivity();
                    activity.getSupportFragmentManager().addOnBackStackChangedListener(()
                            -> activity.getSupportFragmentManager().beginTransaction().detach(new Saved_fragment()).attach(new Saved_fragment()).commit());
                }else if (response.body().get(0).getMessage().equals("Failed")){
                    Toast.makeText(context, "خطا!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<user_model>> call, @NonNull Throwable t) {
                Log.i("response","ERR",t);
            }
        });
    }
}
