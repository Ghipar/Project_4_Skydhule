package com.example.project_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Rview extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rview2);
        recyclerView = findViewById(R.id.rvDataB);
        recyclerView2 = findViewById(R.id.rvDataB2);
        //forecast();
    }
//    public void forecast(){
//
//
//        API api = new API();
//        String url = api.APIforecast;
//        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                listData = new ArrayList<>();
//                listData2 = new ArrayList<>();
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray list = jsonObject.getJSONArray("list");
//                    for (int i = 0; i < list.length(); i++) {
//                        dataModel = new DataModel();
//                        dataModel2 = new DataModel2();
//                        JSONObject arr = list.getJSONObject(i);
//                        JSONObject main = arr.getJSONObject("main");
//                        String tgl = arr.getString("dt_txt");
//                        String humi = main.getString("humidity");
//                        String low = main.getString("temp_min");
//                        String high = main.getString("temp_max");
//                        String tempnow = main.getString("temp");
//                        if (tgl.substring(11, 16).equals("00:00")) {
//                            try {
//                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.ENGLISH);
//                                Date date = sdf.parse(tgl.substring(0, 10));
//                                sdf.applyPattern("EEE");
//                                String str = sdf.format(date);
//                                DecimalFormat formater = new DecimalFormat("#");
//                                dataModel.setJudul(str);
//                                dataModel.setJudul2(humi);
//                                dataModel.setJudul3(formater.format(Double.parseDouble(low.substring(0,2))));
//                                dataModel.setJudul4(formater.format(Double.parseDouble(high.substring(0,2))));
//                                listData.add(dataModel);
//                            } catch (Exception e) {
//                                System.out.println("error");
//                            }
//                        }
//                        String datenow = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
//                        if (tgl.substring(0, 10).equals(datenow)) {
//                            try {
//                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.ENGLISH);
//                                Date date = sdf.parse(tgl.substring(0, 10));
//                                sdf.applyPattern("EEE");
//                                String str = sdf.format(date);
//                                DecimalFormat formater = new DecimalFormat("#");
//                                dataModel2.setJudul(tgl.substring(11, 16));
//                                dataModel2.setJudul2(formater.format(Double.parseDouble(tempnow.substring(0,2))));
//                                dataModel2.setJudul3(humi);
//                                //System.out.println();
//                                listData2.add(dataModel2);
//                            } catch (Exception e) {
//                                System.out.println("error");
//                            }
//                        }
//                    }
//                    linearLayoutManager = new LinearLayoutManager(Rview.this, LinearLayoutManager.VERTICAL, false);
//                    recyclerView.setLayoutManager(linearLayoutManager);
//                    adapterData = new AdapterData(Rview.this, listData);
//                    recyclerView.setAdapter(adapterData);
//                    adapterData.notifyDataSetChanged();
//                    linearLayoutManager2 = new LinearLayoutManager(Rview.this, LinearLayoutManager.HORIZONTAL, false);
//                    recyclerView2.setLayoutManager(linearLayoutManager2);
//                    adapterData2 = new AdapterData2(Rview.this, listData2);
//                    recyclerView2.setAdapter(adapterData2);
//                    adapterData2.notifyDataSetChanged();
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }){
//            //@Override
////            public Map<String, String> getHeaders() throws AuthFailureError {
////                //String tok = sp.getString("token","");
////                HashMap header = new HashMap();
////                header.put("Accept", "application/json");
////                return header;
////            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(Rview.this);
//        requestQueue.add(request);
//    }
}