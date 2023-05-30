package com.example.project_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Splash extends AppCompatActivity {
    Button sigin;
    Button sigup;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sigin = findViewById(R.id.sigin);
        sigup = findViewById(R.id.sigup);
        pref = getSharedPreferences("TokenPrefs", Context.MODE_PRIVATE);
        Dashboard ds = new Dashboard();
//        SharedPreferences.Editor editor = pref.edit();
//        editor.clear();
//        editor.commit();
        String statuslog = pref.getString("token", "");
        if(!statuslog.isEmpty()) {
            Intent intent = new Intent(Splash.this, Dashboard.class);
            startActivity(intent);
        }
        sigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Splash.this, Register.class);
                startActivity(intent);
            }
        });
        sigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Splash.this, Login.class);
                startActivity(intent);
            }
        });
    }
}