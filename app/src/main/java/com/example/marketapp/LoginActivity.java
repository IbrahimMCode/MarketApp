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
import com.example.marketapp.managers.UserManager;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    EditText email_et, password_et;
    LoginViewModel viewModel;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MarketApp.setContext(this);

        if (checkExistantCredentials()) {
            goToListofLocations();
        }

        email_et = findViewById(R.id.registerEmail);
        password_et = findViewById(R.id.inputPassword);

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(email_et.getText().toString()) && !TextUtils.isEmpty(password_et.getText().toString())) {
                    SignInApiCall(email_et.getText().toString(), password_et.getText().toString());
                } else
                    Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SignInApiCall(String email, String password) {
        viewModel.getResponse(email, password).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    goToListofLocations();
                }else{
                    showWrongCredentialsError();
                }
            }
        });
    }

    public void goToListofLocations(){
        Intent intent = new Intent(getApplicationContext(), LocationSelection.class);
        startActivity(intent);
        finish();
    }

    public void showWrongCredentialsError(){
        Snackbar snackbar = Snackbar.make(findViewById(R.id.ConstraintLayout), "Wrong Credentials", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    public boolean checkExistantCredentials(){
        return UserManager.getInstance().isSetLogin("email");
    }

    public void goToRegister(View view){
        intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }


}