package com.example.project_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.common.api.Api;

public class PremPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prem_page);
        ImageView baki = (ImageView) findViewById(R.id.btnback5);
        LinearLayout pro = (LinearLayout) findViewById(R.id.bntPro);
        API api = new API();
        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(api.host + "/login"));
                startActivity(browserIntent);
            }
        });

        baki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PremPage.this, Profile.class);
                startActivity(intent);
            }
        });
    }
}