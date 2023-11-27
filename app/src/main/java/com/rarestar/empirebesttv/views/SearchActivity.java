package com.rarestar.empirebesttv.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.rarestar.empirebesttv.Movie_model.movie_model;
import com.rarestar.empirebesttv.R;
import com.rarestar.empirebesttv.adapter.Movie_Adapter;
import com.rarestar.empirebesttv.retrofit.Retrofit_client;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText editText_search;
    RecyclerView recyclerView_search;
    ImageView ic_back;
    List<movie_model> list;
    Button btn_filter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        processDateTrending();
        init();
        click();
    }
    private void init(){
        editText_search = findViewById(R.id.editText_search);
        ic_back = findViewById(R.id.ic_back);
        recyclerView_search = findViewById(R.id.recyclerView_search);
        btn_filter = findViewById(R.id.btn_filter);

        list= new ArrayList<>();

        layoutManager = new GridLayoutManager(this,MainActivity.screenSize);
        recyclerView_search.setLayoutManager(layoutManager);
        recyclerView_search.setHasFixedSize(true);
    }
    private void click(){
        ic_back.setOnClickListener(this);
        btn_filter.setOnClickListener(this);

        editText_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().equals("")){
                    search(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        if (view == ic_back){
            finish();
        }
        if (view == btn_filter){
            assert btn_filter != null;
            PopupMenu popupMenu = new PopupMenu(SearchActivity.this, btn_filter);

            // Inflating popup menu from popup_menu.xml file
            popupMenu.getMenuInflater().inflate(R.menu.menu_search_filter, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case R.id.menu_name:
                        editText_search.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                                if (!s.toString().equals("")){
                                    search(s.toString());
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                            }
                        });
                        editText_search.setHint("نام فیلم");
                        editText_search.setInputType(InputType.TYPE_CLASS_TEXT);
                        return true;
                    case R.id.menu_genre:
                        editText_search.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                                if (!s.toString().equals("")){
                                    get_Genre(s.toString());
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                            }
                        });
                        editText_search.setInputType(InputType.TYPE_CLASS_TEXT);
                        editText_search.setHint("ژانر");
                        return true;
                    case R.id.menu_year:
                        editText_search.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                                if (!s.toString().equals("")){
                                    get_year(s.toString());
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                            }
                        });
                        editText_search.setHint("سال");
                        editText_search.setInputType(InputType.TYPE_CLASS_NUMBER);
                        return true;
                    default:
                        return true;
                }
            });
            // Showing the popup menu
            popupMenu.show();
        }
    }

    private void search(String key){
        list.clear();
        Call<List<movie_model>> call = Retrofit_client.getInstance().getApi().search(key);
        call.enqueue(new Callback<List<movie_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<movie_model>> call, @NonNull Response<List<movie_model>> response) {
                assert response.body() != null;
                list.addAll(response.body());
                Movie_Adapter adapter = new Movie_Adapter(list,SearchActivity.this);
                recyclerView_search.setAdapter(adapter);
            }
            @Override
            public void onFailure(@NonNull Call<List<movie_model>> call, @NonNull Throwable t) {
                Log.d("Search ","Error",t);
            }
        });
    }

    private void get_Genre(String key) {
        Call<List<movie_model>> call = Retrofit_client.getInstance().getApi().Genre(key);
        call.enqueue(new Callback<List<movie_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<movie_model>> call, @NonNull Response<List<movie_model>> response) {
                List<movie_model> list = response.body();
                Movie_Adapter adapter = new Movie_Adapter(list,SearchActivity.this);
                recyclerView_search.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<movie_model>> call, @NonNull Throwable t) {
                Log.d("Search","",t);
            }
        });
    }

    private void get_year(String key) {
        Call<List<movie_model>> call = Retrofit_client.getInstance().getApi().Year(key);
        call.enqueue(new Callback<List<movie_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<movie_model>> call, @NonNull Response<List<movie_model>> response) {
                List<movie_model> list = response.body();
                Movie_Adapter adapter = new Movie_Adapter(list,SearchActivity.this);
                recyclerView_search.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<movie_model>> call, @NonNull Throwable t) {
                //app.t("Wrong connect!");
            }
        });
    }

    private void processDateTrending(){
        Call<List<movie_model>> call = Retrofit_client.getInstance().getApi().getTrending();
        call.enqueue(new Callback<List<movie_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<movie_model>> call, @NonNull Response<List<movie_model>> response) {
                List<movie_model> list = response.body();
                Movie_Adapter adapter = new Movie_Adapter(list,getApplicationContext());
                recyclerView_search.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<movie_model>> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}