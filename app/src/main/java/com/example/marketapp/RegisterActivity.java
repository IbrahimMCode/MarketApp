package com.example.marketapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marketapp.ViewModels.LoginViewModel;
import com.example.marketapp.ViewModels.RegisterViewModel;
import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity {
    Intent intent;
    RegisterViewModel viewModel;
    EditText email_et, password_et, password_et_2, username_et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);


        email_et = findViewById(R.id.registerEmail);
        password_et = findViewById(R.id.inputPassword);
        password_et_2 = findViewById(R.id.inputPassword2);
        username_et = findViewById(R.id.registerUsername);



        findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_et.getText().toString();
                String password = password_et.getText().toString();
                String password2 = password_et_2.getText().toString();
                String username = username_et.getText().toString();

                if (!password.equals(password2)){
                    Toast.makeText(RegisterActivity.this, "Passwords must Match", Toast.LENGTH_SHORT).show();
                }else{
                    if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password2) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(username)) {
                        RegisterApiCall(email, password, username);
                    } else{
                        Toast.makeText(RegisterActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

    }

    public void RegisterApiCall(String email, String password, String username){
        viewModel.getResponse(email, password, username).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    goToLogin_In();
                }else{
                    showAPIError();
                }
            }
        });
    }

    public void goToLogin_In(){
        intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToLogin(View view){
        goToLogin_In();
    }


    public void showAPIError(){
        Snackbar snackbar = Snackbar.make(findViewById(R.id.ConstraintLayout), "Server Error, User may exist", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

}