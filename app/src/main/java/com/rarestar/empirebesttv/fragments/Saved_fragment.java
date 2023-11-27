package com.rarestar.empirebesttv.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rarestar.empirebesttv.Movie_model.user_model;
import com.rarestar.empirebesttv.R;
import com.rarestar.empirebesttv.adapter.SaveMovieAdapter;
import com.rarestar.empirebesttv.retrofit.Retrofit_client;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Saved_fragment extends Fragment {
    private static final String SHARED_PREF_NAME = "myPref";
    private static final String KEY_NAME = "name";
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SharedPreferences sharedPreferences;
    FloatingActionButton deleteAll;
    TextView textView_NoData;
    SaveMovieAdapter adapter;
    String name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_saved, container, false);

        textView_NoData= rootView.findViewById(R.id.textView_NoData);

        deleteAll= rootView.findViewById(R.id.deleteAll);
        deleteAll.setOnClickListener(view -> {
            DeleteAllSavedData(name);
            recyclerView.refreshDrawableState();
        });
        recyclerView= rootView.findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);

        sharedPreferences = getContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        name = sharedPreferences.getString(KEY_NAME, null);
        if (name != null) {
            processNameMovie(name);
        }
        return rootView;
    }
    private void DeleteAllSavedData(String username) {
        Call<List<user_model>> call = Retrofit_client.getInstance().getApi().DeleteAllSaveMovie(username);
        call.enqueue(new Callback<List<user_model>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<List<user_model>> call, @NonNull Response<List<user_model>> response) {
                assert response.body() != null;
                if (response.body().get(0).getMessage().equals("Success")){
                    adapter.notifyDataSetChanged();
                    recyclerView.refreshDrawableState();
                    Toast.makeText(getContext(), "حذف شد!", Toast.LENGTH_SHORT).show();

                }else if (response.body().get(0).getMessage().equals("Failed")){
                    Toast.makeText(getContext(), "خطا!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<user_model>> call, @NonNull Throwable t) {
                Log.i("response","ERR",t);
            }
        });
    }
    @SuppressLint("NotifyDataSetChanged")
    private void processNameMovie(String username){
        Call<List<user_model>> call = Retrofit_client.getInstance().getApi().NameSaveMovie(username);
        call.enqueue(new Callback<List<user_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<user_model>> call, @NonNull Response<List<user_model>> response) {
                List<user_model> list = response.body();
                adapter = new SaveMovieAdapter(list,getContext());
                recyclerView.setAdapter(adapter);
                if (response.body().isEmpty()){
                    textView_NoData.setVisibility(View.VISIBLE);
                }else {
                    textView_NoData.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<user_model>> call, @NonNull Throwable t) {
                Log.i("test","0",t);
            }
        });
    }
}