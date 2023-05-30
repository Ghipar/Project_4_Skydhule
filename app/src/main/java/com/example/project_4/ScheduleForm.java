package com.example.project_4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ScheduleForm extends AppCompatActivity {

    private LinearLayout button;
    private LinearLayout button2;
    private Button button3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduleform);

        button = findViewById(R.id.btntgl);
        button2 = findViewById(R.id.btntime);
        button3 = findViewById(R.id.btnSimpan);
        ImageView bck = (ImageView) findViewById(R.id.btnbck);

        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleForm.this, LibSchedule.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tglDialog();

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeDialog();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDatus();
            }
        });
    }
    private void tglDialog() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat d = new SimpleDateFormat("dd");
        SimpleDateFormat m = new SimpleDateFormat("MM");
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        TextView gdt = (TextView) findViewById(R.id.getDate);
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                gdt.setText("");
                gdt.append(year+"-"+(month+1)+"-"+day);
            }
        }, Integer.parseInt(y.format(c.getTime())), (Integer.parseInt(m.format(c.getTime())) -1) , Integer.parseInt(d.format(c.getTime())));
        dialog.show();
    }
    private void timeDialog() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("ss");
        SimpleDateFormat m = new SimpleDateFormat("mm");
        SimpleDateFormat h = new SimpleDateFormat("HH");
        TextView gtm = (TextView) findViewById(R.id.getTime);
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int h, int m) {
                gtm.setText("");
                gtm.append(h+":"+String.valueOf(m));
                System.out.println(h+":"+String.valueOf(m));
            }
        }, Integer.parseInt(h.format(c.getTime())), Integer.parseInt(m.format(c.getTime())),  true);
        dialog.show();
    }
    void getDatus() {

        SharedPreferences sp = getApplicationContext().getSharedPreferences("TokenPrefs", Context.MODE_PRIVATE);
        API api = new API();
        String url = api.APIuser;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            private static final String TAG = "Profile";
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String lvl = jsonObject.getString("level");
                    String jmlh = jsonObject.getString("jmlh_kolom");
                    if(Integer.parseInt(jmlh) == 0){
                        kirimData();
                    } else if (lvl.equals("3")) {
                        kirimData();
                    } else if(lvl.equals("1") && Integer.parseInt(jmlh) <= 4){
                        kirimData();
                    } else {
                        //pergi ke halaman promosi
                        Intent intent = new Intent(ScheduleForm.this, PremPage.class);
                        startActivity(intent);
                    }
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
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String tok = sp.getString("token","");
                HashMap header = new HashMap();
                header.put("Accept", "application/json");
                header.put("Authorization", "Bearer " + tok);
                return header;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ScheduleForm.this);
        requestQueue.add(request);
    }
    void kirimData() {
        TextView title = (TextView) findViewById(R.id.txtTitle);
        TextView desc = (TextView) findViewById(R.id.txtDesc);
        TextView gtm = (TextView) findViewById(R.id.getTime);
        TextView gdt = (TextView) findViewById(R.id.getDate);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("TokenPrefs", Context.MODE_PRIVATE);
        String id = sp.getString("id","");
        API api = new API();
        String url = api.APIaddskejul + id;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if(message.equals("Success")) {
                        title.setText("");
                        desc.setText("");
                        gdt.setText("");
                        gtm.setText("");
                        //post data user jlmlh kolom
                        Intent intent = new Intent(ScheduleForm.this, LibSchedule.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                    } else {
                        if(title.getText().toString().isEmpty() || desc.getText().toString().isEmpty()
                                || gdt.getText().toString().isEmpty() || gtm.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Mohon lengkapi data!", Toast.LENGTH_SHORT).show();
                        }  else {
                            Toast.makeText(getApplicationContext(), "Gagal!", Toast.LENGTH_SHORT).show();
                        }
                    }
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
            protected Map<String, String> getParams(){
                String id = sp.getString("id","");
                Map<String, String> kirim_form = new HashMap<String, String>();
                kirim_form.put("judul", title.getText().toString());
                kirim_form.put("deskripsi", desc.getText().toString());
                kirim_form.put("tanggal", gdt.getText().toString());
                kirim_form.put("jam", gtm.getText().toString());
                kirim_form.put("status", "1");
                kirim_form.put("id", id);
                return kirim_form;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ScheduleForm.this);
        requestQueue.add(request);
    }
}