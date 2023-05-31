package com.example.project_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Berita extends AppCompatActivity {
    RecyclerView recyclerView4;
    LinearLayoutManager linearLayoutManager4;
    AdapterData4 adapterData4;
    List<DataModel4> listData4;
    DataModel4 dataModel4;
    public void getBer() {
        ImageView nodat = (ImageView)findViewById(R.id.nocataber);
        API api = new API();
        String url = api.APIgetberita;
        SharedPreferences sp = getApplicationContext().getSharedPreferences("TokenPrefs", Context.MODE_PRIVATE);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listData4 = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        dataModel4 = new DataModel4();
                        JSONObject arr = data.getJSONObject(i);
                        String gmbr = arr.getString("gambar");
                        String title = arr.getString("judul_berita");
                        String desc = arr.getString("dekripsi_berita");
                        String ling = arr.getString("link");

                        if (data.length() == 0) {
                            nodat.setVisibility(View.VISIBLE);
                        } else {
                            nodat.setVisibility(View.GONE);
                            dataModel4.setJudul(title);
                            dataModel4.setDesc(desc);
                            //dataModel4.setGambar(api.host + "/frontend/assets/img/berita_image/" + gmbr);
                            dataModel4.setGambar(gmbr);
                            dataModel4.setLing(ling);
                            listData4.add(dataModel4);
                        }

                    }
                    linearLayoutManager4 = new LinearLayoutManager(Berita.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView4.setLayoutManager(linearLayoutManager4);
                    adapterData4 = new AdapterData4(Berita.this, listData4);
                    recyclerView4.setAdapter(adapterData4);
                    adapterData4.notifyDataSetChanged();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
            }
        }) {
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Berita.this);
        requestQueue.add(request);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);
        recyclerView4 = findViewById(R.id.rvData5);
        getBer();

        ImageView back = (ImageView) findViewById(R.id.btnback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Berita.this, Profile.class);
                startActivity(intent);
            }
        });
    }


}