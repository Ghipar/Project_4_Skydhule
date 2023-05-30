package com.example.project_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;


public class Dashboard extends AppCompatActivity {
    FusedLocationProviderClient fusedLocationProviderClient;
    private final static int REQUEST_CODE = 100;
    Button search;
    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    LinearLayoutManager linearLayoutManager;
    AdapterData adapterData;
    List<DataModel> listData;
    DataModel dataModel;
    LinearLayoutManager linearLayoutManager2;
    AdapterData2 adapterData2;
    List<DataModel2> listData2;
    DataModel2 dataModel2;
    
    public void cuacaHarin(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Dashboard.this);
        TextView suhuharin = (TextView)findViewById(R.id.suhharin);
        TextView lowtempharin= (TextView)findViewById(R.id.lowtempharin);
        TextView hightempharin = (TextView)findViewById(R.id.hightempharin);
        TextView deskripcua = (TextView)findViewById(R.id.weatherharin);
        TextView city = (TextView)findViewById(R.id.nameLoca);
        TextView humiharin = (TextView)findViewById(R.id.humidityharin);
        TextView nauawn = (TextView)findViewById(R.id.nauawn);

        API api = new API();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location != null) {
                        Geocoder geocoder = new Geocoder(Dashboard.this, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            String url = api.APIcuaca +"lat=" + addresses.get(0).getLatitude() + "&lon="+ addresses.get(0).getLongitude() + "&appid=" + "9b3af350d678e7433a1fb7bf5bafff8e";
                            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        //GetJsonObject
                                        JSONObject main = jsonObject.getJSONObject("main");
                                        JSONObject cloud = jsonObject.getJSONObject("clouds");
                                        //GetJsonArray
                                        JSONArray weather = jsonObject.getJSONArray("weather");
                                        //GetString
                                        //main
                                        String temp = main.getString("temp");
                                        String tempMnharin = main.getString("temp_min");
                                        String tempMxharin = main.getString("temp_max");
                                        String humiday = main.getString("humidity");
                                        String nauaw = cloud.getString("all");
                                        //jsonObject
                                        String locCity = jsonObject.getString("name");
                                        for (int i = 0; i < weather.length(); i++) {
                                            JSONObject arr = weather.getJSONObject(i);
                                            String namcua = arr.getString("main");
                                            deskripcua.append(namcua);
                                        }

                                        DecimalFormat formater = new DecimalFormat("#");
                                        String suhrin = formater.format(Double.parseDouble(temp));
                                        String lowrin = formater.format(Double.parseDouble(tempMnharin));
                                        String highrin = formater.format(Double.parseDouble(tempMxharin));
                                        suhuharin.append((Integer.parseInt(suhrin)-273) + "°C");
                                        lowtempharin.append("Low " + (Integer.parseInt(lowrin)-273) + "°C |");
                                        hightempharin.append("High " + (Integer.parseInt(highrin)-273) + "°C");
                                        city.append(locCity);
                                        humiharin.append(humiday + "%");
                                        nauawn.append(nauaw + "%");
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }){
                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(Dashboard.this);
                            requestQueue.add(request);
                        } catch (IOException e) {
                            throw new RuntimeException(e);

                        }
                    }
                }
            });
        } else
        {
            askPermission();
        }
            
    }

    public void cuacaHarinsear(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Dashboard.this);
        TextView suhuharin = (TextView)findViewById(R.id.suhharin);
        TextView lowtempharin= (TextView)findViewById(R.id.lowtempharin);
        TextView hightempharin = (TextView)findViewById(R.id.hightempharin);
        TextView deskripcua = (TextView)findViewById(R.id.weatherharin);
        TextView city = (TextView)findViewById(R.id.nameLoca);
        TextView humiharin = (TextView)findViewById(R.id.humidityharin);
        TextView nauawn = (TextView)findViewById(R.id.nauawn);
        TextView sear = (TextView) findViewById(R.id.txtsearch);
        API api = new API();
        String url = api.APIcuaca +"q="+ sear.getText().toString() + "&appid=" + "9b3af350d678e7433a1fb7bf5bafff8e";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //GetJsonObject
                    JSONObject main = jsonObject.getJSONObject("main");
                    JSONObject cloud = jsonObject.getJSONObject("clouds");
                    //GetJsonArray
                    JSONArray weather = jsonObject.getJSONArray("weather");
                    //GetString
                    //main
                    String temp = main.getString("temp");
                    String tempMnharin = main.getString("temp_min");
                    String tempMxharin = main.getString("temp_max");
                    String humiday = main.getString("humidity");
                    String nauaw = cloud.getString("all");
                    //jsonObject
                    String locCity = jsonObject.getString("name");
                    for (int i = 0; i < weather.length(); i++) {
                        JSONObject arr = weather.getJSONObject(i);
                        String namcua = arr.getString("main");
                        deskripcua.append(namcua);
                    }

                    DecimalFormat formater = new DecimalFormat("#");
                    String suhrin = formater.format(Double.parseDouble(temp));
                    String lowrin = formater.format(Double.parseDouble(tempMnharin));
                    String highrin = formater.format(Double.parseDouble(tempMxharin));
                    suhuharin.append((Integer.parseInt(suhrin)-273) + "°C");
                    lowtempharin.append("Low " + (Integer.parseInt(lowrin)-273) + "°C |");
                    hightempharin.append("High " + (Integer.parseInt(highrin)-273) + "°C");
                    city.append(locCity);
                    humiharin.append(humiday + "%");
                    nauawn.append(nauaw + "%");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Nama kota tidak ditemukan", Toast.LENGTH_SHORT).show();
                cuacaHarin();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Dashboard.this);
        requestQueue.add(request);

    }




    public void forecast(){

        API api = new API();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location != null) {
                        Geocoder geocoder = new Geocoder(Dashboard.this, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            String url = api.APIforecast +"lat=" + addresses.get(0).getLatitude() + "&lon="+ addresses.get(0).getLongitude() + "&appid=" + "9b3af350d678e7433a1fb7bf5bafff8e";
                            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    listData = new ArrayList<>();
                                    listData2 = new ArrayList<>();
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONArray list = jsonObject.getJSONArray("list");
                                        for (int i = 0; i < list.length(); i++) {
                                            dataModel = new DataModel();
                                            dataModel2 = new DataModel2();
                                            JSONObject arr = list.getJSONObject(i);
                                            JSONObject main = arr.getJSONObject("main");
                                            String tgl = arr.getString("dt_txt");
                                            String humi = main.getString("humidity");
                                            String low = main.getString("temp_min");
                                            String high = main.getString("temp_max");
                                            String tempnow = main.getString("temp");
                                            if (tgl.substring(11, 16).equals("00:00")) {
                                                try {
                                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.ENGLISH);
                                                    Date date = sdf.parse(tgl.substring(0, 10));
                                                    sdf.applyPattern("EEE");
                                                    String str = sdf.format(date);
                                                    DecimalFormat formater = new DecimalFormat("#");
                                                    String lowrin = formater.format(Double.parseDouble(low));
                                                    String highrin = formater.format(Double.parseDouble(high));
                                                    dataModel.setJudul(str);
                                                    dataModel.setJudul2(humi);
                                                    dataModel.setJudul3((Integer.parseInt(lowrin)-273));
                                                    dataModel.setJudul4((Integer.parseInt(highrin)-273));
                                                    listData.add(dataModel);
                                                } catch (Exception e) {
                                                    System.out.println("error");
                                                }
                                            }
                                            String datenow = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                                            if (tgl.substring(0, 10).equals(datenow)) {
                                                try {
                                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.ENGLISH);
                                                    Date date = sdf.parse(tgl.substring(0, 10));
                                                    sdf.applyPattern("EEE");
                                                    String str = sdf.format(date);
                                                    DecimalFormat formater = new DecimalFormat("#");
                                                    String tempn = formater.format(Double.parseDouble(tempnow));
                                                    dataModel2.setJudul(tgl.substring(11, 16));
                                                    dataModel2.setJudul2((Integer.parseInt(tempn)-273));
                                                    dataModel2.setJudul3(humi);
                                                    //System.out.println();
                                                    listData2.add(dataModel2);
                                                } catch (Exception e) {
                                                    System.out.println("error");
                                                }
                                            }
                                        }
                                        linearLayoutManager = new LinearLayoutManager(Dashboard.this, LinearLayoutManager.VERTICAL, false);
                                        recyclerView.setLayoutManager(linearLayoutManager);
                                        adapterData = new AdapterData(Dashboard.this, listData);
                                        recyclerView.setAdapter(adapterData);
                                        adapterData.notifyDataSetChanged();
                                        linearLayoutManager2 = new LinearLayoutManager(Dashboard.this, LinearLayoutManager.HORIZONTAL, false);
                                        recyclerView2.setLayoutManager(linearLayoutManager2);
                                        adapterData2 = new AdapterData2(Dashboard.this, listData2);
                                        recyclerView2.setAdapter(adapterData2);
                                        adapterData2.notifyDataSetChanged();
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }){
                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(Dashboard.this);
                            requestQueue.add(request);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        } else
        {
            askPermission();
        }

    }

    public void forecastSearch(){
        TextView sear = (TextView) findViewById(R.id.txtsearch);
        API api = new API();
        String url = api.APIforecast +"q="+ sear.getText().toString() + "&appid=" + "9b3af350d678e7433a1fb7bf5bafff8e";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listData = new ArrayList<>();
                listData2 = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray list = jsonObject.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        dataModel = new DataModel();
                        dataModel2 = new DataModel2();
                        JSONObject arr = list.getJSONObject(i);
                        JSONObject main = arr.getJSONObject("main");
                        String tgl = arr.getString("dt_txt");
                        String humi = main.getString("humidity");
                        String low = main.getString("temp_min");
                        String high = main.getString("temp_max");
                        String tempnow = main.getString("temp");
                        if (tgl.substring(11, 16).equals("00:00")) {
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.ENGLISH);
                                Date date = sdf.parse(tgl.substring(0, 10));
                                sdf.applyPattern("EEE");
                                String str = sdf.format(date);
                                DecimalFormat formater = new DecimalFormat("#");
                                String lowrin = formater.format(Double.parseDouble(low));
                                String highrin = formater.format(Double.parseDouble(high));
                                dataModel.setJudul(str);
                                dataModel.setJudul2(humi);
                                dataModel.setJudul3((Integer.parseInt(lowrin)-273));
                                dataModel.setJudul4((Integer.parseInt(highrin)-273));
                                listData.add(dataModel);
                            } catch (Exception e) {
                                System.out.println("error");
                            }
                        }
                        String datenow = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                        if (tgl.substring(0, 10).equals(datenow)) {
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.ENGLISH);
                                Date date = sdf.parse(tgl.substring(0, 10));
                                sdf.applyPattern("EEE");
                                String str = sdf.format(date);
                                DecimalFormat formater = new DecimalFormat("#");
                                String tempn = formater.format(Double.parseDouble(tempnow));
                                dataModel2.setJudul(tgl.substring(11, 16));
                                dataModel2.setJudul2((Integer.parseInt(tempn)-273));
                                dataModel2.setJudul3(humi);
                                //System.out.println();
                                listData2.add(dataModel2);
                            } catch (Exception e) {
                                System.out.println("error");
                            }
                        }
                    }
                    linearLayoutManager = new LinearLayoutManager(Dashboard.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    adapterData = new AdapterData(Dashboard.this, listData);
                    recyclerView.setAdapter(adapterData);
                    adapterData.notifyDataSetChanged();
                    linearLayoutManager2 = new LinearLayoutManager(Dashboard.this, LinearLayoutManager.HORIZONTAL, false);
                    recyclerView2.setLayoutManager(linearLayoutManager2);
                    adapterData2 = new AdapterData2(Dashboard.this, listData2);
                    recyclerView2.setAdapter(adapterData2);
                    adapterData2.notifyDataSetChanged();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Nama kota tidak ditemukan", Toast.LENGTH_SHORT).show();
                forecast();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Dashboard.this);
        requestQueue.add(request);

    }
    private void askPermission() {
        ActivityCompat.requestPermissions(Dashboard.this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cuacaHarin();
                forecast();
            } else {
                Toast.makeText(this, "Required Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setIc() {
        ImageView ic = (ImageView) findViewById(R.id.iccuaharini);
        TextView nameIc = (TextView) findViewById(R.id.weatherharin);
        GifImageView anirain = (GifImageView) findViewById(R.id.aniRain);
        System.out.println("asdasd");
        if(nameIc.getText().toString().equalsIgnoreCase("clouds"))  {
            ic.setBackground(getDrawable(R.drawable.ic_clouds));
            anirain.setBackground(null);
        } else if(nameIc.getText().toString().equalsIgnoreCase("few clouds")){
            ic.setBackground(getDrawable(R.drawable.ic_clouds));
            anirain.setBackground(null);
        } else if(nameIc.getText().toString().equalsIgnoreCase("scattered clouds")){
            ic.setBackground(getDrawable(R.drawable.ic_scattered));
            anirain.setBackground(null);
        } else if(nameIc.getText().toString().equalsIgnoreCase("broken clouds")){
            ic.setBackground(getDrawable(R.drawable.ic_broken));
            anirain.setBackground(null);
        } else if(nameIc.getText().toString().equalsIgnoreCase("shower rain")){
            ic.setBackground(getDrawable(R.drawable.ic_showermorn));
        } else if(nameIc.getText().toString().equalsIgnoreCase("rain")){
            ic.setBackground(getDrawable(R.drawable.ic_rainmorn));
        } else if(nameIc.getText().toString().equalsIgnoreCase("thunderstorm")){
            ic.setBackground(getDrawable(R.drawable.ic_thunderstorm));
        } else if(nameIc.getText().toString().equalsIgnoreCase("snow")){
            ic.setBackground(getDrawable(R.drawable.ic_scattered));
        } else if(nameIc.getText().toString().equalsIgnoreCase("clear sky")){
            ic.setBackground(getDrawable(R.drawable.ic_sun));
            anirain.setBackground(null);
        } else if(nameIc.getText().toString().equalsIgnoreCase("mist")){
            ic.setBackground(getDrawable(R.drawable.ic_scattered));
            anirain.setBackground(null);
        } else if(nameIc.getText().toString().equalsIgnoreCase("clear")){
            ic.setBackground(getDrawable(R.drawable.ic_sun));
            anirain.setBackground(null);
        }
    }

    public void cekDay() {
        ConstraintLayout pg = (ConstraintLayout) findViewById(R.id.pageDash);
        TextView suhharin = (TextView) findViewById(R.id.suhharin);
        TextView low = (TextView) findViewById(R.id.lowtempharin);
        TextView high = (TextView) findViewById(R.id.hightempharin);
        TextView loca = (TextView) findViewById(R.id.nameLoca);
        ImageView ic_prof = (ImageView) findViewById(R.id.dashprof);
        ImageView ic_loc = (ImageView) findViewById(R.id.logLoca);
        ImageView ic_sear = (ImageView) findViewById(R.id.search);
        ImageView ic_lin = (ImageView) findViewById(R.id.linkertas);
        ImageView ic_wnkir = (ImageView) findViewById(R.id.awnkir);
        ImageView ic_wnkan = (ImageView) findViewById(R.id.awnkan);
        ImageView ic_hum = (ImageView) findViewById(R.id.logHum);
        ImageView ic_uv = (ImageView) findViewById(R.id.log_uv);
        ImageView ic_vis = (ImageView) findViewById(R.id.logVis);
        ImageView ic_term = (ImageView) findViewById(R.id.logterm);
        ImageView ic_rn = (ImageView) findViewById(R.id.lograin);
        ImageView ic_awn = (ImageView) findViewById(R.id.logClo);
        TextView nm_hum = (TextView) findViewById(R.id.nameHum);
        TextView vl_hum = (TextView) findViewById(R.id.humidityharin);
        TextView witername = (TextView) findViewById(R.id.weatherharin);
        TextView name_uv = (TextView) findViewById(R.id.name_uv);
        TextView val_uv = (TextView) findViewById(R.id.val_uv);
        TextView name_vis = (TextView) findViewById(R.id.nameVis);
        TextView val_vis = (TextView) findViewById(R.id.valVis);
        TextView name_term = (TextView) findViewById(R.id.nameterm);
        TextView val_term = (TextView) findViewById(R.id.valterm);
        TextView name_rn = (TextView) findViewById(R.id.namerain);
        TextView val_rn = (TextView) findViewById(R.id.valrain);
        TextView name_clo = (TextView) findViewById(R.id.nameClo);
        TextView val_clo = (TextView) findViewById(R.id.nauawn);
        TextView sear = (TextView) findViewById(R.id.txtsearch);
        LinearLayout ln = (LinearLayout) findViewById(R.id.linearLayout2);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        try {
            Date date_to = formatter.parse("17:00");
            Date dateNow = formatter.parse(formatter.format(date));
            if (dateNow.before(date_to)) {
                pg.setBackground(getDrawable(R.drawable.custom2));
                ic_prof.setBackground(getDrawable(R.drawable.ic_prof));
                ic_loc.setBackground(getDrawable(R.drawable.ic_location));
                ic_sear.setBackground(getDrawable(R.drawable.ic_search));
                ic_lin.setBackground(getDrawable(R.drawable.ling));
                ic_wnkir.setBackground(getDrawable(R.drawable.ic_awn2));
                ic_wnkan.setBackground(getDrawable(R.drawable.ic_awnn));
                ic_hum.setBackground(getDrawable(R.drawable.ic_humidity));
                ic_uv.setBackground(getDrawable(R.drawable.ic_uv));
                ic_vis.setBackground(getDrawable(R.drawable.ic_eye));
                ic_term.setBackground(getDrawable(R.drawable.ic_termo));
                ic_rn.setBackground(getDrawable(R.drawable.ic_umbrel));
                ic_awn.setBackground(getDrawable(R.drawable.ic_nauawan));
                suhharin.setTextColor(getColor(R.color.black));
                low.setTextColor(getColor(R.color.black));
                high.setTextColor(getColor(R.color.black));
                witername.setTextColor(getColor(R.color.black));
                nm_hum.setTextColor(getColor(R.color.black));
                vl_hum.setTextColor(getColor(R.color.black));
                name_uv.setTextColor(getColor(R.color.black));
                val_uv.setTextColor(getColor(R.color.black));
                name_vis.setTextColor(getColor(R.color.black));
                val_vis.setTextColor(getColor(R.color.black));
                name_term.setTextColor(getColor(R.color.black));
                val_term.setTextColor(getColor(R.color.black));
                name_rn.setTextColor(getColor(R.color.black));
                val_rn.setTextColor(getColor(R.color.black));
                name_clo.setTextColor(getColor(R.color.black));
                val_clo.setTextColor(getColor(R.color.black));
                ln.setBackground(getDrawable(R.drawable.custom_input5));
                sear.setTextColor(getColor(R.color.black));
            } else {
                pg.setBackground(getDrawable(R.drawable.custom3));
                ic_prof.setBackground(getDrawable(R.drawable.ic_profw));
                ic_loc.setBackground(getDrawable(R.drawable.ic_locationw));
                ic_sear.setBackground(getDrawable(R.drawable.ic_searchw));
                ic_lin.setBackground(getDrawable(R.drawable.lingnight));
                ic_wnkir.setBackground(getDrawable(R.drawable.ic_awannight));
                ic_wnkan.setBackground(getDrawable(R.drawable.ic_awannight2));
                ic_hum.setBackground(getDrawable(R.drawable.ic_humidityw));
                ic_uv.setBackground(getDrawable(R.drawable.ic_uvw));
                ic_vis.setBackground(getDrawable(R.drawable.ic_eyew));
                ic_term.setBackground(getDrawable(R.drawable.ic_termow));
                ic_rn.setBackground(getDrawable(R.drawable.ic_umbrelw));
                ic_awn.setBackground(getDrawable(R.drawable.ic_nauawanw));
                suhharin.setTextColor(getColor(R.color.white));
                low.setTextColor(getColor(R.color.white));
                high.setTextColor(getColor(R.color.white));
                loca.setTextColor(getColor(R.color.white));
                witername.setTextColor(getColor(R.color.white));
                nm_hum.setTextColor(getColor(R.color.white));
                vl_hum.setTextColor(getColor(R.color.white));
                name_uv.setTextColor(getColor(R.color.white));
                val_uv.setTextColor(getColor(R.color.white));
                name_vis.setTextColor(getColor(R.color.white));
                val_vis.setTextColor(getColor(R.color.white));
                name_term.setTextColor(getColor(R.color.white));
                val_term.setTextColor(getColor(R.color.white));
                name_rn.setTextColor(getColor(R.color.white));
                val_rn.setTextColor(getColor(R.color.white));
                name_clo.setTextColor(getColor(R.color.white));
                val_clo.setTextColor(getColor(R.color.white));
                sear.setTextColor(getColor(R.color.white));
                ln.setBackground(getDrawable(R.drawable.custom_input6));
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        recyclerView2 = findViewById(R.id.rvData3);
        recyclerView = findViewById(R.id.rvData2);
        TextView sear = (TextView) findViewById(R.id.txtsearch);
        TextView nme = (TextView) findViewById(R.id.nameLoca);
        TextView wea = (TextView) findViewById(R.id.weatherharin);
        TextView suh = (TextView) findViewById(R.id.suhharin);
        TextView low = (TextView) findViewById(R.id.lowtempharin);
        TextView high = (TextView) findViewById(R.id.hightempharin);
        TextView hum = (TextView) findViewById(R.id.humidityharin);
        TextView nau = (TextView) findViewById(R.id.nauawn);
        ImageView logsear = (ImageView) findViewById(R.id.search);
        sear.setVisibility(View.GONE);
        //permisalan jika search.getText.isEmpty maka biasaa
        System.out.println(sear.getText().toString());
        cuacaHarin();
        forecast();
        //else
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            System.out.println("Fetching FCM registration token faileds");
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();
                        // Log and toast
                        System.out.println(token);
                        Toast.makeText(Dashboard.this, "Your device registration", Toast.LENGTH_SHORT).show();
                    }
                });
        //
        logsear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nme.getVisibility() == View.VISIBLE){
                    System.out.println("holaaaa");
                    sear.setVisibility(View.VISIBLE);
                    nme.setVisibility(View.GONE);
                } else if(sear.getText().toString().isEmpty()) {
                    System.out.println("halooooo");
                    sear.setVisibility(View.GONE);
                    nme.setVisibility(View.VISIBLE);
                } else {
                    //eksekusi perintah
                    nme.setText("");
                    wea.setText("");
                    suh.setText("");
                    low.setText("");
                    high.setText("");
                    hum.setText("");
                    nau.setText("");
                    forecastSearch();
                    cuacaHarinsear();
                    setIc();
                    sear.setText("");
                    sear.setVisibility(View.GONE);
                    nme.setVisibility(View.VISIBLE);
                }
            }
        });
        cekDay();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setIc();
            }
        }, 10000);
        ImageView dashprof  = (ImageView) findViewById(R.id.dashprof);
        dashprof.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(Dashboard.this, Profile.class);
                   startActivity(intent);
               }
           });
    }

}