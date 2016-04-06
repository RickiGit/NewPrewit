package com.altrovis.newprewit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.altrovis.newprewit.Bussines.Login.LoginAsyncTask;

public class ActivityLogin extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextPassword;
    Button buttonMasuk;

    String username, password, accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inisialisasiLayout();

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        alreadyLogin();
        goToPageHome();
    }

    public void goToPageHome()
    {
        buttonMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editTextUsername.getText().toString();
                password = editTextPassword.getText().toString();
                if(username.equals("") || password.equals("")){
                    Toast.makeText(ActivityLogin.this, "Username dan password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else{
                    new LoginAsyncTask(ActivityLogin.this, username, password).execute();
                }
            }
        });
    }

    public void inisialisasiLayout()
    {
        editTextUsername = (EditText)findViewById(R.id.EditTextUsername);
        editTextPassword = (EditText)findViewById(R.id.EditTextPassword);
        buttonMasuk = (Button)findViewById(R.id.ButtonMasuk);
    }

    private void alreadyLogin(){
        SharedPreferences login = getSharedPreferences("login", MODE_PRIVATE);
        username = login.getString("username", "");
        accessToken = login.getString("accesstoken","");

        if(!username.equalsIgnoreCase("") && !accessToken.equalsIgnoreCase("")){
            Intent intent = new Intent(ActivityLogin.this, ActivityMain.class);
            startActivity(intent);
            finish();
        }
    }
}
