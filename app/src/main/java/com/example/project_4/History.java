package com.example.project_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

public class History extends AppCompatActivity {
    RecyclerView recyclerView3;
    LinearLayoutManager linearLayoutManager3;
    AdapterData3 adapterData3;
    List<DataModel3> listData3;
    DataModel3 dataModel3;

    public void getSche() {
        ImageView lognodat = (ImageView)findViewById(R.id.nocata);
        API api = new API();
        String url = api.APIgetskejul;
        //date
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd", Locale.getDefault());
        String formattedDate = df.format(c);
        //time
        LocalTime waktuskrg = LocalTime.now();
        String formtime = waktuskrg.toString().substring(0,5);
        LocalTime lbhsjam = waktuskrg.plusHours(1);
        String formtimesjm = waktuskrg.toString().substring(0,5);
        //
        SharedPreferences sp = getApplicationContext().getSharedPreferences("TokenPrefs", Context.MODE_PRIVATE);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listData3 = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        dataModel3 = new DataModel3();
                        JSONObject arr = data.getJSONObject(i);
                        String id = sp.getString("id","");
                        String Usid = arr.getString("id");
                        String title = arr.getString("judul");
                        String desc = arr.getString("deskripsi");
                        String tgl = arr.getString("tanggal");
                        String time = arr.getString("jam");
                        String timedb = time.substring(0,5);
                        String kode = arr.getString("kode_schedule");
                        String stus = arr.getString("status");
                        if(Usid.equals(id) && stus.equals("2")) {
                            lognodat.setVisibility(View.GONE);
                            dataModel3.setJudul(title);
                            dataModel3.setDesc(desc);
                            dataModel3.setDate(tgl);
                            dataModel3.setTime(time);
                            System.out.println("halooo");
                            listData3.add(dataModel3);
                        } else {
                            if(Usid.equals(id) && stus.equals("1")){
                                System.out.println("nothing");
                            } else {
                                lognodat.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    linearLayoutManager3 = new LinearLayoutManager(History.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView3.setLayoutManager(linearLayoutManager3);
                    adapterData3 = new AdapterData3(History.this, listData3);
                    recyclerView3.setAdapter(adapterData3);
                    adapterData3.notifyDataSetChanged();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(History.this);
        requestQueue.add(request);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        recyclerView3 = findViewById(R.id.rvData6);
        getSche();
        ImageView back = (ImageView) findViewById(R.id.btnback2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(History.this, Profile.class);
                startActivity(intent);
            }
        });
    }
}