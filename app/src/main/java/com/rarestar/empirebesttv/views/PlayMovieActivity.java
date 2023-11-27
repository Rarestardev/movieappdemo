package com.rarestar.empirebesttv.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.rarestar.empirebesttv.Movie_model.MovieDownloadAndPlay_model;
import com.rarestar.empirebesttv.Movie_model.comment_model;
import com.rarestar.empirebesttv.Movie_model.likePost_model;
import com.rarestar.empirebesttv.Movie_model.movie_model;
import com.rarestar.empirebesttv.Movie_model.serial_model;
import com.rarestar.empirebesttv.Movie_model.superstars_model;
import com.rarestar.empirebesttv.Movie_model.user_model;
import com.rarestar.empirebesttv.R;
import com.rarestar.empirebesttv.adapter.Actors_Adapter;
import com.rarestar.empirebesttv.adapter.DownloadBoxAdapter;
import com.rarestar.empirebesttv.adapter.PlayBoxAdapter;
import com.rarestar.empirebesttv.adapter.Serial_season_Adapter;
import com.rarestar.empirebesttv.adapter.Superstars_Adapter;
import com.rarestar.empirebesttv.adapter.CommentAdapter;
import com.rarestar.empirebesttv.adapter.Movie_Adapter;
import com.rarestar.empirebesttv.retrofit.Retrofit_client;
import com.rarestar.empirebesttv.video_player.VideoPlayer;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayMovieActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView_actors,recyclerView_views,recyclerView_comment,recyclerView_play
            ,recyclerView_serials,recyclerView_download;
    RelativeLayout image;
    TextView name, genre, desc, director, age,
            user_rate,time, imdb,textView_moreComment,movieName_dialog,
            textView_status,textView_country,title_dialog;
    Button btn_login,btn_register,btn_sendComment;
    EditText editText_commentText;
    ImageView movie_image,poster,icon_saved,close_dialog,icon_like;
    CardView cardView_saved, cardView_play, cardView_download, cardView_share,
            play_trailer,cardView_like, cardView_comment ,cardView_back,cardView_status;
    String Year, Genre, Desc, Director, ImageLink,
            Age, Rate, Imdb,Country,Status,Duration;
    public static String Name;
    public static String Category;
    public static String trailerUrl;
    public static boolean DownloadingMovie = false;
    public static boolean Trailer = false;
    SharedPreferences sharedPreferences;
    ScrollView playMovieLayout;
    List<comment_model> list;
    List<serial_model> SerialList;
    private static final String SHARED_PREF_NAME = "myPref";
    private static final String KEY_NAME = "name";
    Dialog dialogRegister,playBoxDialog,sendCommentDialog,dialog_DownloadBox,dialog_serialPLay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play_movie);
        init();
        getDataFromActivities();
        get_Genre(Genre);
        setFields();
        //check save movie in database
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_NAME,null);
        if (name != null){
            CheckSaveMovieInDataBase(name,Name);
            CheckLikeMovieInDataBase(name,Name);
        }
        click();
    }
    private void getDataFromActivities(){
        if (Actors_Adapter.actors_adapter){
            Name = Actors_Adapter.Movie;
            Year = Actors_Adapter.Year;
            Genre = Actors_Adapter.Genre;
            Desc = Actors_Adapter.Desc;
            Director = Actors_Adapter.Director;
            ImageLink = Actors_Adapter.Poster_link;
            Age = Actors_Adapter.Age;
            Imdb = Actors_Adapter.Rate_imdb;
            Rate = Actors_Adapter.Rate_user;
            Category = Actors_Adapter.Category;
            Country = Actors_Adapter.Country;
            Status = Actors_Adapter.Status;
            Duration = Actors_Adapter.Duration;
        }else {
            Name = getIntent().getStringExtra("MovieName");
            Year = getIntent().getStringExtra("Year");
            Genre = getIntent().getStringExtra("Genre");
            Desc = getIntent().getStringExtra("Desc");
            Director = getIntent().getStringExtra("Director");
            ImageLink = getIntent().getStringExtra("ImageLink");
            Age = getIntent().getStringExtra("Age");
            Imdb = getIntent().getStringExtra("IMDB");
            Rate = getIntent().getStringExtra("Rate");
            Category = getIntent().getStringExtra("Category");
            Country = getIntent().getStringExtra("Country");
            Status = getIntent().getStringExtra("Status");
            Duration = getIntent().getStringExtra("Duration");
        }

        Comment(Name);
    }
    private void init() {
        //TextViews
        name = findViewById(R.id.name);
        time = findViewById(R.id.time);
        genre = findViewById(R.id.genre);
        desc = findViewById(R.id.desc);
        director = findViewById(R.id.director);
        textView_status = findViewById(R.id.textView_status);
        movie_image = findViewById(R.id.movie_image);
        age = findViewById(R.id.age);
        textView_country = findViewById(R.id.textView_country);
        user_rate = findViewById(R.id.user_rate);
        imdb = findViewById(R.id.imdb);
        poster = findViewById(R.id.poster);
        icon_like = findViewById(R.id.icon_like);
        textView_moreComment = findViewById(R.id.textView_moreComment);
        image = findViewById(R.id.image);
        icon_saved = findViewById(R.id.icon_saved);
        playMovieLayout = findViewById(R.id.playMovieLayout);
        //CardViews
        cardView_saved = findViewById(R.id.cardView_saved);
        cardView_download = findViewById(R.id.cardView_download);
        cardView_play = findViewById(R.id.cardView_play);
        cardView_share = findViewById(R.id.cardView_share);
        cardView_like = findViewById(R.id.cardView_like);
        cardView_comment = findViewById(R.id.cardView_comment);
        cardView_back = findViewById(R.id.cardView_back);
        play_trailer = findViewById(R.id.play_trailer);
        cardView_status = findViewById(R.id.cardView_status);
        //init recyclerView for actors
        recyclerView_actors = findViewById(R.id.recyclerView_actors);
        recyclerView_actors.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false));

        recyclerView_views = findViewById(R.id.recyclerView_views);
        recyclerView_views.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false));

        recyclerView_comment = findViewById(R.id.recyclerView_comment);
        recyclerView_comment.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false));
        recyclerView_comment.refreshDrawableState();

        searchActors(Movie_Adapter.Movie);

        if (MainActivity.screenSize > 3){
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    900);
            image.setLayoutParams(params);

            poster.getLayoutParams().height = 900;
        }
    }
    @SuppressLint("SetTextI18n")
    private void setFields() {
        name.setText(Name + "-" + Year);
        genre.setText(Genre);
        desc.setText(Desc);
        if (Duration == null){
            Duration = "0000";
        }
        time.setText(Duration);
        director.setText(Director);
        age.setText(Age);
        user_rate.setText(Rate);
        imdb.setText(Imdb);

        Picasso.get().load(ImageLink).into(movie_image);
        Picasso.get().load(ImageLink).into(poster);

        if (Country.isEmpty()){
            textView_country.setText("-");
        }else {
            textView_country.setText(Country);
        }
        if (Status.isEmpty()){
            cardView_status.setVisibility(View.GONE);
        }else {
            cardView_status.setVisibility(View.VISIBLE);
            textView_status.setText(Status);
        }
    }
    private void click() {
        cardView_play.setOnClickListener(this);
        cardView_download.setOnClickListener(this);
        cardView_share.setOnClickListener(this);
        cardView_comment.setOnClickListener(this);
        cardView_saved.setOnClickListener(this);
        cardView_back.setOnClickListener(this);
        play_trailer.setOnClickListener(this);

        textView_moreComment.setOnClickListener(this);

        cardView_like.setOnClickListener(firstListener);
    }
    View.OnClickListener firstListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
            String name = sharedPreferences.getString(KEY_NAME,null);
            if (name != null){
                LikePost(name,Name);
            }else {
                DialogRegisterAccount();
            }
            cardView_like.setOnClickListener(secondListener);
        }
    };
    View.OnClickListener secondListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
            String name = sharedPreferences.getString(KEY_NAME,null);
            if (name != null){
                DisLikePost(name,Name);
            }
            cardView_like.setOnClickListener(firstListener);
        }
    };
    @Override
    public void onClick(View view) {
        if (view == cardView_play) {
            Trailer = false;
            switch (Category) {
                case "Movie":
                    openDialog_Play();
                    break;
                case "Animation":
                    openDialog_Play();
                    break;
                case "Serial":
                    openDialog_PlaySerial();
                    DownloadingMovie = true;
                    break;
            }
        }
        if (view == cardView_download) {
            Trailer = false;
            switch (Category) {
                case "Movie":
                    openDialog_DownloadBox();
                    break;
                case "Animation":
                    openDialog_DownloadBox();
                    break;
                case "Serial":
                    openDialog_PlaySerial();
                    title_dialog.setText("فایل مورد نظر را انتخاب کنید:");
                    DownloadingMovie = false;
                    break;
            }
        }
        if (view == cardView_saved) {
            sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
            String name = sharedPreferences.getString(KEY_NAME,null);
            if (name != null){
                if (Duration == null){
                    Duration = "00:00";
                }
                SaveMovieInDataBase(name,Name,Desc,Category,Genre,Director
                        ,Imdb,Rate,ImageLink,Age,
                        Year,Status,Country,Duration);
            }else {
                DialogRegisterAccount();
            }
        }
        if (view == cardView_share) {
            Intent intentSend = new Intent();
            intentSend.setAction(Intent.ACTION_SEND);
            intentSend.putExtra(Intent.EXTRA_TEXT,PlayBoxAdapter.LINK);
            intentSend.setType("text/plain");
            Intent share = Intent.createChooser(intentSend, null);
            startActivity(share);
        }
        if (view == cardView_comment) {
            DialogSendComment();
        }
        if (view == textView_moreComment){
            Intent getComment = new Intent(getApplicationContext(), CommentActivity.class);
            getComment.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(getComment);
        }
        if (view == play_trailer){
            Play_Trailer(Name);
            Trailer = true;
        }
        if (view == cardView_back){
            finish();
        }
    }

    private void Play_Trailer(String movie_name) {
        Call<List<movie_model>> call = Retrofit_client.getInstance().getApi().playTrailer(movie_name);
        call.enqueue(new Callback<List<movie_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<movie_model>> call, @NonNull Response<List<movie_model>> response) {
                if (response.body() != null){
                    trailerUrl = response.body().get(0).getTrailer_link();
                    Intent intent = new Intent(PlayMovieActivity.this, VideoPlayer.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(PlayMovieActivity.this, "لینک تریلر قرار داده نشده است!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<movie_model>> call, @NonNull Throwable t) {
                Log.d("Trailer","ErrorLink",t);
            }
        });
    }
    private void openDialog_PlaySerial() {
        dialog_serialPLay = new Dialog(this);
        dialog_serialPLay.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_serialPLay.setContentView(R.layout.bottom_dialog_serial_play);
        dialog_serialPLay.show();
        dialog_serialPLay.getWindow().setLayout(1350 ,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_serialPLay.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_serialPLay.getWindow().getAttributes().windowAnimations= R.style.DialogAnimation;
        dialog_serialPLay.getWindow().setGravity(Gravity.BOTTOM);

        recyclerView_serials= dialog_serialPLay.findViewById(R.id.recyclerView_serials);
        title_dialog= dialog_serialPLay.findViewById(R.id.title_dialog);
        recyclerView_serials.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));
        boxSerialPlayLink();
    }
    private void boxSerialPlayLink(){
        Call<List<serial_model>> call = Retrofit_client.getInstance().getApi().SeasonSerial(Name);
        call.enqueue(new Callback<List<serial_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<serial_model>> call, @NonNull Response<List<serial_model>> response) {
                SerialList = response.body();
                if (response.body() != null){
                    Serial_season_Adapter adapter = new Serial_season_Adapter(SerialList,PlayMovieActivity.this);
                    recyclerView_serials.setAdapter(adapter);
                    System.out.println(Name);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<serial_model>> call, @NonNull Throwable t) {
                Log.d("Link","ERROR_LINK",t);
            }
        });
    }
    private void DialogRegisterAccount() {
        dialogRegister = new Dialog(PlayMovieActivity.this);
        dialogRegister.setContentView(R.layout.register_dialog);
        btn_register = dialogRegister.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(view -> {
            Intent Register = new Intent(PlayMovieActivity.this,RegisterActivity.class);
            Register.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Register);
            dialogRegister.dismiss();
        });
        btn_login = dialogRegister.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(view -> {
            Intent Login = new Intent(PlayMovieActivity.this,LoginActivity.class);
            Login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Login);
            dialogRegister.dismiss();
        });
        close_dialog = dialogRegister.findViewById(R.id.close_dialog);
        close_dialog.setOnClickListener(view -> dialogRegister.dismiss());
        dialogRegister.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogRegister.getWindow().setGravity(Gravity.CENTER);
        dialogRegister.show();
    }
    private void SaveMovieInDataBase(String username, String movie,String desc, String category,String genre,String director,
                                     String rate_imdb,String rate_user,String poster_link
                                     ,String age,String year,String status,String country,String duration){

        Call<List<user_model>> call = Retrofit_client.getInstance().getApi().SaveMovie(username,movie,desc,category,
                genre,director,rate_imdb,rate_user,poster_link,age,year,status,country,duration);
        call.enqueue(new Callback<List<user_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<user_model>> call, Response<List<user_model>> response) {
                if (response.body().get(0).getMessage().equals("Success")){
                    icon_saved.setImageResource(R.drawable.baseline_bookmark_added);
                    Toast.makeText(PlayMovieActivity.this, "ذخیره شد", Toast.LENGTH_SHORT).show();
                }else if (response.body().get(0).getMessage().equals("Failed")){
                    DeleteSaveMovieInDataBase(username,movie);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<user_model>> call, @NonNull Throwable t) {
                Log.i("response","ERROR",t);
            }
        });
    }
    private void DeleteSaveMovieInDataBase(String username,String movie) {
        Call<List<user_model>> call = Retrofit_client.getInstance().getApi().DeleteSaveMovie(username,movie);
        call.enqueue(new Callback<List<user_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<user_model>> call, Response<List<user_model>> response) {
                if (response.body().get(0).getMessage().equals("Success")){
                    icon_saved.setImageResource(R.drawable.baseline_bookmark_border_24);
                    Toast.makeText(PlayMovieActivity.this, "حذف شد!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<user_model>> call, @NonNull Throwable t) {
                Log.i("response","ERR",t);
            }
        });
    }
    private void CheckSaveMovieInDataBase(String username,String movie) {
        Call<List<user_model>> call = Retrofit_client.getInstance().getApi().CheckSaveMovie(username,movie);
        call.enqueue(new Callback<List<user_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<user_model>> call, Response<List<user_model>> response) {
                if (response.body().get(0).getMessage().equals("Successful")){
                    icon_saved.setImageResource(R.drawable.baseline_bookmark_added);
                }else if (response.body().get(0).getMessage().equals("Failed")){
                    icon_saved.setImageResource(R.drawable.baseline_bookmark_border_24);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<user_model>> call, @NonNull Throwable t) {
                Log.i("response","ERR",t);
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (dialogRegister == null || playBoxDialog == null || sendCommentDialog == null){
            finish();
        }else {
            dialogRegister.dismiss();
            playBoxDialog.dismiss();
        }
        super.onBackPressed();
    }
    private void searchActors(String key){
        Call<List<superstars_model>> call = Retrofit_client.getInstance().getApi().searchActors(key);
        call.enqueue(new Callback<List<superstars_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<superstars_model>> call, @NonNull Response<List<superstars_model>> response) {
                assert response.body() != null;
                List<superstars_model> list = response.body();
                Superstars_Adapter adapter = new Superstars_Adapter(list,PlayMovieActivity.this);
                recyclerView_actors.setAdapter(adapter);
            }
            @Override
            public void onFailure(@NonNull Call<List<superstars_model>> call, @NonNull Throwable t) {
                Log.d("PlayMovie","error",t);
            }
        });
    }
    private void get_Genre(String key) {
        Call<List<movie_model>> call = Retrofit_client.getInstance().getApi().Genre(key);
        call.enqueue(new Callback<List<movie_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<movie_model>> call, @NonNull Response<List<movie_model>> response) {
                List<movie_model> list = response.body();
                Movie_Adapter adapter = new Movie_Adapter(list,PlayMovieActivity.this);
                recyclerView_views.setAdapter(adapter);
            }
            @Override
            public void onFailure(@NonNull Call<List<movie_model>> call, @NonNull Throwable t) {
                Log.d("PlayMovie","error",t);
            }
        });
    }
    private void openDialog_Play() {
        playBoxDialog = new Dialog(this);
        playBoxDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        playBoxDialog.setContentView(R.layout.bottom_dialog_play);
        playBoxDialog.show();
        playBoxDialog.getWindow().setLayout(1350,ViewGroup.LayoutParams.WRAP_CONTENT);
        playBoxDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        playBoxDialog.getWindow().getAttributes().windowAnimations= R.style.DialogAnimation;
        playBoxDialog.getWindow().setGravity(Gravity.BOTTOM);
        recyclerView_play= playBoxDialog.findViewById(R.id.recyclerView_play);
        recyclerView_play.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));
        boxPlayLink();
    }
    private void boxPlayLink(){
        Call<List<MovieDownloadAndPlay_model>> call = Retrofit_client.getInstance().getApi().getLinkPlayMovie(Name);
        call.enqueue(new Callback<List<MovieDownloadAndPlay_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<MovieDownloadAndPlay_model>> call, @NonNull Response<List<MovieDownloadAndPlay_model>> response) {
                if (response.body() != null){
                    List<MovieDownloadAndPlay_model> movieDownloadAndPlay_models = response.body();
                    PlayBoxAdapter adapter = new PlayBoxAdapter(movieDownloadAndPlay_models,PlayMovieActivity.this);
                    recyclerView_play.setAdapter(adapter);
                }else {
                    Toast.makeText(PlayMovieActivity.this, "null", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<MovieDownloadAndPlay_model>> call, @NonNull Throwable t) {
                Log.d("Link","ERROR_LINK",t);
            }
        });
    }
    private void DialogSendComment() {
        sendCommentDialog = new Dialog(PlayMovieActivity.this);
        sendCommentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sendCommentDialog.setContentView(R.layout.insert_comment);
        sendCommentDialog.show();
        sendCommentDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        sendCommentDialog.getWindow().setGravity(Gravity.CENTER);

        close_dialog = sendCommentDialog.findViewById(R.id.close_dialog);
        btn_sendComment = sendCommentDialog.findViewById(R.id.btn_sendComment);
        editText_commentText = sendCommentDialog.findViewById(R.id.editText_commentText);
        movieName_dialog = sendCommentDialog.findViewById(R.id.movieName_dialog);

        close_dialog.setOnClickListener(view -> sendCommentDialog.dismiss());

        movieName_dialog.setText(Name);
        btn_sendComment.setOnClickListener(view -> {
            sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
            String name = sharedPreferences.getString(KEY_NAME,null);
            if (name != null && !editText_commentText.getText().toString().isEmpty()){
                InsertCommentInDataBase(name,Name,editText_commentText.getText().toString());
            }else if (name == null){
                DialogRegisterAccount();
            }else if (editText_commentText.getText().toString().isEmpty()){
                editText_commentText.setError("خطا ! لطفا کادر خالی را پر کنید");
            }
        });
    }
    @SuppressLint("NotifyDataSetChanged")
    private void InsertCommentInDataBase(String username , String movieName , String post) {
        Call<List<comment_model>> call = Retrofit_client.getInstance().getApi().InsertComment(username,movieName,post);
        call.enqueue(new Callback<List<comment_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<comment_model>> call, @NonNull Response<List<comment_model>> response) {
                assert response.body() != null;
                if (response.body().get(0).getMessage().equals("Success")){
                    Toast.makeText(PlayMovieActivity.this, "ارسال شد!", Toast.LENGTH_SHORT).show();
                    sendCommentDialog.dismiss();
                }else {
                    Toast.makeText(PlayMovieActivity.this, "ارسال ناموفق", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<comment_model>> call, @NonNull Throwable t) {
                Log.i("response","ERR",t);
            }
        });
    }
    private void Comment(String key){
        Call<List<comment_model>> call = Retrofit_client.getInstance().getApi().GetCommentUsers(key);
        call.enqueue(new Callback<List<comment_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<comment_model>> call, @NonNull Response<List<comment_model>> response) {
                if (response.body() != null){
                    list = response.body();
                    CommentAdapter adapter = new CommentAdapter(list,PlayMovieActivity.this);
                    adapter.notifyDataSetChanged();
                    recyclerView_comment.refreshDrawableState();
                    recyclerView_comment.setAdapter(adapter);
                }else {
                    Toast.makeText(PlayMovieActivity.this, "null", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<comment_model>> call, @NonNull Throwable t) {
                Log.d("comment","error",t);
            }
        });
    }
    private void openDialog_DownloadBox() {
        dialog_DownloadBox = new Dialog(this);
        dialog_DownloadBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_DownloadBox.setContentView(R.layout.bottom_dialog);
        dialog_DownloadBox.show();
        dialog_DownloadBox.getWindow().setLayout(1350,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_DownloadBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_DownloadBox.getWindow().getAttributes().windowAnimations= R.style.DialogAnimation;
        dialog_DownloadBox.getWindow().setGravity(Gravity.BOTTOM);

        recyclerView_download = dialog_DownloadBox.findViewById(R.id.recyclerView_download);
        recyclerView_download.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        boxDownloadLink();
    }
    private void boxDownloadLink(){
        Call<List<MovieDownloadAndPlay_model>> call = Retrofit_client.getInstance().getApi().getLinkPlayMovie(Name);
        call.enqueue(new Callback<List<MovieDownloadAndPlay_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<MovieDownloadAndPlay_model>> call, @NonNull Response<List<MovieDownloadAndPlay_model>> response) {
                if (response.body() != null){
                    List<MovieDownloadAndPlay_model> movieDownloadAndPlay_models = response.body();
                    DownloadBoxAdapter adapter = new DownloadBoxAdapter(movieDownloadAndPlay_models,PlayMovieActivity.this);
                    recyclerView_download.setAdapter(adapter);
                }else {
                    Toast.makeText(PlayMovieActivity.this, "null", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<MovieDownloadAndPlay_model>> call, @NonNull Throwable t) {
                Log.d("Link","ERROR_LINK",t);
            }
        });
    }

    private void LikePost(String username,String movie_name) {
        Call<List<likePost_model>> call = Retrofit_client.getInstance().getApi().LikePost(username,movie_name);
        call.enqueue(new Callback<List<likePost_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<likePost_model>> call, @NonNull Response<List<likePost_model>> response) {
                if (response.body() != null){
                    if (response.body().get(0).getMessage().equals("Success")){
                        icon_like.setImageResource(R.drawable.baseline_favorite_24);
                        Toast.makeText(PlayMovieActivity.this, "لایک شد.", Toast.LENGTH_SHORT).show();
                    }else if (response.body().get(0).getMessage().equals("Failed")) {
                        Toast.makeText(PlayMovieActivity.this, "ناموفق!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<likePost_model>> call, @NonNull Throwable t) {
                Log.d("LikePost","Error insert like",t);
            }
        });
    }
    private void DisLikePost(String username,String movie_name){
        Call<List<likePost_model>> call = Retrofit_client.getInstance().getApi().DisLikePost(username,movie_name);
        call.enqueue(new Callback<List<likePost_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<likePost_model>> call, @NonNull Response<List<likePost_model>> response) {
                if (response.body() != null){
                    if (response.body().get(0).getMessage().equals("Success")){
                        icon_like.setImageResource(R.drawable.baseline_favorite_border_24);
                        Toast.makeText(PlayMovieActivity.this, "لایک برداشته شد.", Toast.LENGTH_SHORT).show();
                    }else if (response.body().get(0).getMessage().equals("Failed")) {
                        Toast.makeText(PlayMovieActivity.this, "ناموفق!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<likePost_model>> call, @NonNull Throwable t) {
                Log.d("LikePost","Error insert like",t);
            }
        });
    }
    private void CheckLikeMovieInDataBase(String username,String movie_name){
        Call<List<likePost_model>> call = Retrofit_client.getInstance().getApi().CheckLikePost(username,movie_name);
        call.enqueue(new Callback<List<likePost_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<likePost_model>> call, @NonNull Response<List<likePost_model>> response) {
                if (response.body() != null){
                    if (response.body().get(0).getMessage().equals("Successful")){
                        icon_like.setImageResource(R.drawable.baseline_favorite_24);
                    }else if (response.body().get(0).getMessage().equals("Failed")){
                        icon_like.setImageResource(R.drawable.baseline_favorite_border_24);
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<likePost_model>> call, @NonNull Throwable t) {
                Log.i("response","ERR",t);
            }
        });
    }
}

