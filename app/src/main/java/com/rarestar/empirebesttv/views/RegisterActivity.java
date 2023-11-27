package com.rarestar.empirebesttv.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.rarestar.empirebesttv.Movie_model.user_model;
import com.rarestar.empirebesttv.R;
import com.rarestar.empirebesttv.retrofit.Retrofit_client;

import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView ic_back;
    AppCompatButton btn_register;
    TextView textView_login;
    EditText editText_username,editText_email,editText_password,editText_rePassword;
    String UserName,Password,Email,rePassword;
    CheckBox checkbox_showPass;
    LinearLayout root_layout;
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        click();
    }
    private void init(){
        ic_back = findViewById(R.id.ic_back);
        editText_username = findViewById(R.id.editText_username);
        editText_email = findViewById(R.id.editText_email);
        editText_password = findViewById(R.id.editText_password);
        editText_rePassword = findViewById(R.id.editText_rePassword);
        textView_login = findViewById(R.id.textView_login);
        btn_register = findViewById(R.id.btn_register);
        root_layout = findViewById(R.id.root_layout);

        checkbox_showPass = findViewById(R.id.checkbox_showPass);
    }
    private void click() {
        ic_back.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        textView_login.setOnClickListener(this);
        checkbox_showPass.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                editText_password.setTransformationMethod(new HideReturnsTransformationMethod());
                editText_rePassword.setTransformationMethod(new HideReturnsTransformationMethod());
            }else {
                editText_password.setTransformationMethod(new PasswordTransformationMethod());
                editText_rePassword.setTransformationMethod(new PasswordTransformationMethod());
            }
        });
        checkedFields();
    }
    @Override
    public void onClick(View view) {
        if (view == ic_back){
            finish();
        }
        if (view == btn_register){
            if (editText_username.getText().toString().isEmpty() || editText_password.getText().toString().isEmpty() ||
                    editText_email.getText().toString().isEmpty() || editText_rePassword.getText().toString().isEmpty()){
                Toast.makeText(this, "لطفا همه فیلد ها را پر کنید!", Toast.LENGTH_SHORT).show();
            }else {
                Add_User();
            }
        }
        if (view == textView_login){
            Intent LoginActivity = new Intent(RegisterActivity.this,LoginActivity.class);
            LoginActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(LoginActivity);
            finish();
        }
    }
    private void Add_User() {
        Call<List<user_model>> call = Retrofit_client.getInstance().getApi().AddUser(UserName,Email,rePassword);
        call.enqueue(new Callback<List<user_model>>() {
            @Override
            public void onResponse(@NonNull Call<List<user_model>> call, Response<List<user_model>> response) {
                if (response.body().get(0).getMessage().equals("Success")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("تبریک")
                            .setMessage("اطلاعات شما ثبت شد")
                            .setPositiveButton("باشه", (dialogInterface, i) -> {
                                dialogInterface.dismiss();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            });
                    builder.setCancelable(true);
                    builder.show();
                }else {
                    editText_username.setText("");
                    editText_email.setText("");
                    editText_password.setText("");
                    editText_rePassword.setText("");
                    Snackbar snackbar = Snackbar.make(root_layout,"نام کاربری یا ایمیل شما تکراری است!", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("صفحه ورود", view -> {
                        Intent LoginActivity = new Intent(RegisterActivity.this,LoginActivity.class);
                        LoginActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(LoginActivity);
                        finish();
                    });
                    snackbar.show();
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
        finish();
        super.onBackPressed();
    }
    public void checkedFields() {
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
                if (editable.length() < 8 == editable.length() > 32) {
                    editText_password.setError("نام کاربری حداقل 8 کارکتر حداکثر 32");
                }
            }
        });
        editText_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().equals("")){
                    Email = s.toString();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!EMAIL_ADDRESS_PATTERN.matcher(editable).matches()){
                    editText_email.setError("ایمیل نا معتبر");
                }
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
                if (editable.length() < 8) {
                    editText_password.setError("گذر واژه باید بیشتر از 8 کارکتر باشد!");
                }
            }
        });
        editText_rePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editText_rePassword.getText().toString().equals(Password)){
                    editText_rePassword.setError("گذر واژه اشتباه است");
                }else {
                    rePassword = editText_rePassword.getText().toString().trim();
                }
            }
        });
    }
}