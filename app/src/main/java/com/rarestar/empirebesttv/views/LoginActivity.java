package com.rarestar.empirebesttv.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    ImageView ic_back;
    EditText editText_username,editText_password,editText_email,editText_newPassword;
    CheckBox checkbox_password;
    AppCompatButton btn_login;
    Button recovery_pass,new_pass_save;
    TextView textView_register,forgetPassword;
    SharedPreferences sharedPreferences;
    private String UserName,Password;
    private String newPass;
    private String Email;

    private static final String SHARED_PREF_NAME = "myPref";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASS = "pass";
    Dialog updatePasswordDialog,forgetPassDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        click();
    }
    private void init(){
        ic_back = findViewById(R.id.ic_back);
        editText_username = findViewById(R.id.editText_username);
        editText_password = findViewById(R.id.editText_password);
        checkbox_password = findViewById(R.id.checkbox_password);
        btn_login = findViewById(R.id.btn_login);
        textView_register = findViewById(R.id.textView_register);
        forgetPassword = findViewById(R.id.forgetPassword);

        checkedFields();
    }
    private void click(){
        ic_back.setOnClickListener(view -> finish());
        checkbox_password.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                editText_password.setTransformationMethod(new HideReturnsTransformationMethod());
            }else {
                editText_password.setTransformationMethod(new PasswordTransformationMethod());
            }
        });
        btn_login.setOnClickListener(view -> {
            CheckData(UserName,Password);
        });
        textView_register.setOnClickListener(view -> {
            Intent Register = new Intent(LoginActivity.this,RegisterActivity.class);
            Register.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Register);
            finish();
        });
        forgetPassword.setOnClickListener(view -> {
            ForgetPasswordDialog();
        });
    }
    private void CheckData(String username,String pass){
        Call<List<user_model>> call = Retrofit_client.getInstance().getApi().Check(username,pass);
        call.enqueue(new Callback<List<user_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<user_model>> call, @NonNull Response<List<user_model>> response) {
                assert response.body() != null;
                if (response.body().isEmpty()){
                    Toast.makeText(LoginActivity.this, "اطلاعات نا معتبر", Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("تبریک")
                            .setMessage("وارد شدید")
                            .setPositiveButton("باشه", (dialogInterface, i) -> {
                                dialogInterface.dismiss();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(KEY_NAME,editText_username.getText().toString());
                                editor.putString(KEY_PASS,editText_password.getText().toString());
                                editor.apply();
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            });
                    builder.setCancelable(true);
                    builder.show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<user_model>> call, @NonNull Throwable t) {
                Log.i("test","0",t);
            }
        });
    }

    private void ForgetPasswordDialog() {
        forgetPassDialog = new Dialog(LoginActivity.this);
        forgetPassDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        forgetPassDialog.setContentView(R.layout.forget_password_dialog);
        forgetPassDialog.show();
        forgetPassDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        forgetPassDialog.getWindow().setGravity(Gravity.CENTER);
        editText_email = forgetPassDialog.findViewById(R.id.editText_email);
        recovery_pass = forgetPassDialog.findViewById(R.id.recovery_pass);
        recovery_pass.setOnClickListener(view -> {
            Email = editText_email.getText().toString();
            CheckUserInDataBase(Email);
        });
    }
    private void CheckUserInDataBase(String email) {
        Call<List<user_model>> call = Retrofit_client.getInstance().getApi().CheckEmail(email);
        call.enqueue(new Callback<List<user_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<user_model>> call, @NonNull Response<List<user_model>> response) {
                assert response.body() != null;
                if (response.body().get(0).getMessage().equals("Success")){
                    forgetPassDialog.dismiss();
                    newPasswordDialog();
                }else if (response.body().get(0).getMessage().equals("Failed")){
                    Toast.makeText(LoginActivity.this, "آدرس ایمیل وحود ندارد!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<user_model>> call, @NonNull Throwable t) {
                Log.i("test","0",t);
            }
        });
    }

    private void newPasswordDialog() {
        updatePasswordDialog = new Dialog(LoginActivity.this);
        updatePasswordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        updatePasswordDialog.setContentView(R.layout.new_password_dialog);
        updatePasswordDialog.show();
        updatePasswordDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        updatePasswordDialog.getWindow().setGravity(Gravity.CENTER);

        editText_newPassword = updatePasswordDialog.findViewById(R.id.editText_newPassword);

        new_pass_save = updatePasswordDialog.findViewById(R.id.new_pass_save);

        new_pass_save.setOnClickListener(view -> {
            if (editText_newPassword.getText().toString().isEmpty()){
                editText_newPassword.setError("کادر خالی است!");
            }else{
                newPass = editText_newPassword.getText().toString();
                UpdatePasswordInDataBase(Email,newPass);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (updatePasswordDialog == null || forgetPassDialog == null){
            finish();
        }else {
            updatePasswordDialog.dismiss();
            forgetPassDialog.dismiss();
        }
        super.onBackPressed();
    }
    private void checkedFields(){
        editText_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().equals("")){
                    UserName = s.toString();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editText_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().equals("")){
                    Password = s.toString();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_NAME,null);
        if (name != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void UpdatePasswordInDataBase(String email , String newPass) {
        Call<List<user_model>> call = Retrofit_client.getInstance().getApi().UpdatePass(email,newPass);
        call.enqueue(new Callback<List<user_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<user_model>> call, Response<List<user_model>> response) {
                if (response.body().get(0).getMessage().equals("Updated")){
                    Toast.makeText(LoginActivity.this, "گذر واژه تغییر کرد!", Toast.LENGTH_SHORT).show();
                    forgetPassDialog.dismiss();
                    updatePasswordDialog.dismiss();
                }else if (response.body().get(0).getMessage().equals("Failed")){
                    Toast.makeText(LoginActivity.this, "ایمیل اشتباه است!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<user_model>> call, @NonNull Throwable t) {
                Log.i("response","ERR",t);
            }
        });
    }
}