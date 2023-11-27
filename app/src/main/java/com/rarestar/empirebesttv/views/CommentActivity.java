package com.rarestar.empirebesttv.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rarestar.empirebesttv.Movie_model.comment_model;
import com.rarestar.empirebesttv.R;
import com.rarestar.empirebesttv.adapter.CommentAdapter;
import com.rarestar.empirebesttv.retrofit.Retrofit_client;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity {
    RecyclerView views_comment;
    ImageView ic_back,close_dialog;
    Button btn_sendComment,btn_register,btn_login;
    EditText editText_commentText;
    TextView movieName_dialog;
    RelativeLayout sendComment;
    List<comment_model> list;
    String Movie_Name;
    String textComment;
    Dialog sendCommentDialog,dialogRegister;
    SharedPreferences sharedPreferences;
    CommentAdapter adapter;
    private static final String SHARED_PREF_NAME = "myPref";
    private static final String KEY_NAME = "name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Movie_Name = PlayMovieActivity.Name;
        init();
        Comment(Movie_Name);
    }
    private void init(){
        views_comment = findViewById(R.id.views_comment);
        views_comment.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        ic_back = findViewById(R.id.ic_back);
        ic_back.setOnClickListener(view -> finish());

        sendComment = findViewById(R.id.sendComment);
        sendComment.setOnClickListener(view -> DialogSendComment());
    }

    private void DialogSendComment() {
        sendCommentDialog = new Dialog(CommentActivity.this);
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

        movieName_dialog.setText(Movie_Name);
        editText_commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()){
                    editText_commentText.setError("هیچ متنی وجود ندارد!");
                }else {
                    textComment = editText_commentText.getText().toString();
                }
            }
        });

        btn_sendComment.setOnClickListener(view -> {
            if (textComment.isEmpty()){
                Toast.makeText(CommentActivity.this, "کادر ارسال نظر خالی است!", Toast.LENGTH_SHORT).show();
            }else {
                sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
                String name = sharedPreferences.getString(KEY_NAME,null);
                if (name != null){
                    InsertCommentInDataBase(name,Movie_Name,textComment);
                }else {
                    DialogRegisterAccount();
                }
            }
        });
    }
    private void InsertCommentInDataBase(String username , String movieName , String post) {
        Call<List<comment_model>> call = Retrofit_client.getInstance().getApi().InsertComment(username,movieName,post);
        call.enqueue(new Callback<List<comment_model>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<List<comment_model>> call, Response<List<comment_model>> response) {
                if (response.body().get(0).getMessage().equals("Success")){
                    Toast.makeText(CommentActivity.this, "ارسال شد!", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    views_comment.refreshDrawableState();
                    sendCommentDialog.dismiss();
                }else {
                    Toast.makeText(CommentActivity.this, "ارسال ناموفق", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<comment_model>> call, @NonNull Throwable t) {
                Log.i("response","ERR",t);
            }
        });
    }
    private void DialogRegisterAccount() {
        dialogRegister = new Dialog(CommentActivity.this);
        dialogRegister.setContentView(R.layout.register_dialog);
        btn_register = dialogRegister.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(view -> {
            Intent Register = new Intent(CommentActivity.this,RegisterActivity.class);
            Register.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Register);
            dialogRegister.dismiss();
        });
        btn_login = dialogRegister.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(view -> {
            Intent Login = new Intent(CommentActivity.this,LoginActivity.class);
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

    private void Comment(String key){
        Call<List<comment_model>> call = Retrofit_client.getInstance().getApi().GetCommentUsers(key);
        call.enqueue(new Callback<List<comment_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<comment_model>> call, @NonNull Response<List<comment_model>> response) {
                if (response.body() != null){
                    list = response.body();
                    adapter = new CommentAdapter(list,CommentActivity.this);
                    views_comment.setAdapter(adapter);
                }else {
                    Toast.makeText(CommentActivity.this, "null", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<comment_model>> call, @NonNull Throwable t) {
                Log.d("comment","error",t);
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (sendCommentDialog == null){
            finish();
        }else {
            sendCommentDialog.dismiss();
        }
        super.onBackPressed();
    }
}