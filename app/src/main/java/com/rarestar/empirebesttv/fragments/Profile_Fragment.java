package com.rarestar.empirebesttv.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rarestar.empirebesttv.Movie_model.user_model;
import com.rarestar.empirebesttv.R;
import com.rarestar.empirebesttv.retrofit.Retrofit_client;
import com.rarestar.empirebesttv.views.LoginActivity;
import com.rarestar.empirebesttv.views.MainActivity;
import com.rarestar.empirebesttv.views.RegisterActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Profile_Fragment extends Fragment {
    ImageView profile_image;
    Button btn_register,btn_login,back;
    EditText editText_email,editText_password;
    CheckBox checkbox_password;
    Button logout;
    SharedPreferences sharedPreferences;
    TextView user_name;
    private static final String SHARED_PREF_NAME = "myPref";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASS = "pass";
    private static String USERNAME = "username";
    private static String EMAIL= "email";
    private static String PASS= "pass";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        profile_image = rootView.findViewById(R.id.profile_image);
        btn_register = rootView.findViewById(R.id.btn_register);
        btn_login = rootView.findViewById(R.id.btn_login);
        user_name = rootView.findViewById(R.id.user_name);
        CheckUser();
        return rootView;
    }
    @SuppressLint("SetTextI18n")
    private void CheckUser(){
        sharedPreferences = getContext().getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_NAME,null);
        String pass = sharedPreferences.getString(KEY_PASS,null);
        if (name != null || pass != null){
            get_UserInfo(name,pass);
            USERNAME = name;
            btn_register.setVisibility(View.GONE);
            btn_login.setText("خروج از حساب");
            btn_login.setOnClickListener(view -> DeleteAccountDialog());
        }
        else {
            btn_login.setText("ورود");
            btn_login.setOnClickListener(view -> {
                Intent Login = new Intent(getContext(),LoginActivity.class);
                Login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Login);
            });
            btn_register.setVisibility(View.VISIBLE);
            btn_register.setOnClickListener(view -> {
                Intent Register = new Intent(getContext(),RegisterActivity.class);
                Register.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Register);
            });
        }
    }
    private void get_UserInfo(String username,String pass){
        Call<List<user_model>> call = Retrofit_client.getInstance().getApi().get_UserInfo(username,pass);
        call.enqueue(new Callback<List<user_model>>() {
            @SuppressLint("CommitTransaction")
            @Override
            public void onResponse(@NonNull Call<List<user_model>> call, @NonNull Response<List<user_model>> response) {
                assert response.body() != null;
                if (!response.body().isEmpty()){
                    user_name.setText(USERNAME);
                    Profile_Fragment profile_fragment = new Profile_Fragment();
                    assert getFragmentManager() != null;
                    getFragmentManager().beginTransaction().detach(profile_fragment).attach(profile_fragment);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<user_model>> call, @NonNull Throwable t) {
                Log.i("test","0",t);
            }
        });
    }
    private void DeleteAccountDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete_account);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        editText_email = dialog.findViewById(R.id.editText_email);
        editText_password = dialog.findViewById(R.id.editText_password);
        checkbox_password = dialog.findViewById(R.id.checkbox_password);
        back = dialog.findViewById(R.id.back);
        back.setOnClickListener(view -> dialog.dismiss());
        logout = dialog.findViewById(R.id.logout);
        checkbox_password.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                editText_password.setTransformationMethod(new HideReturnsTransformationMethod());
            }else {
                editText_password.setTransformationMethod(new PasswordTransformationMethod());
            }
        });
        logout.setOnClickListener(view -> {
            sharedPreferences = getContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            if (!editText_email.getText().toString().isEmpty() || !editText_password.getText().toString().isEmpty()) {
                EMAIL = editText_email.getText().toString().trim();
                PASS = editText_password.getText().toString().trim();
                DeleteAccountFromDataBase();
            }else{
                Toast.makeText(getContext(), "لطفا فیلد های خالی را پر کنید", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void DeleteAccountFromDataBase() {
        Call<List<user_model>> call = Retrofit_client.getInstance().getApi().LogOut(EMAIL,PASS);
        call.enqueue(new Callback<List<user_model>>() {
            @SuppressLint("CommitTransaction")
            @Override
            public void onResponse(@NonNull Call<List<user_model>> call, @NonNull Response<List<user_model>> response) {
                assert response.body() != null;
                if (response.body().get(0).getMessage().equals("Success")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear().apply();
                    Toast.makeText(getContext(), "حساب کاربری حذف شد", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else if (response.body().get(0).getMessage().equals("Failed")){
                    Toast.makeText(getContext(), "نا موفق", Toast.LENGTH_SHORT).show();
                    editText_email.setText("");
                    editText_password.setText("");
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<user_model>> call, @NonNull Throwable t) {
                Log.i("test","0",t);
            }
        });
    }
}