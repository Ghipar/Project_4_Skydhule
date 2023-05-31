package com.example.project_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    Button btnlogin;
    Button btnreg;
    EditText txtusername;
    EditText txtpass;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnlogin = findViewById(R.id.btnlogin);
        txtusername = findViewById(R.id.txtusername);
        txtpass = findViewById(R.id.txtpass);
        TextView fgt = (TextView)findViewById(R.id.fgt);
        TextView sgup = (TextView)findViewById(R.id.sgup);
        pref = getSharedPreferences("TokenPrefs", Context.MODE_PRIVATE);

        fgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, LupPass.class);
                startActivity(intent);
            }
        });
        sgup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtusername.getText().toString().isEmpty() || txtpass.getText().toString().isEmpty())
                {
                    Toast.makeText(Login.this, "Mohon lengkapi data", Toast.LENGTH_SHORT).show();
                } else {
                    kirimData();
                }
            }

            void kirimData() {
                API api = new API();
                //String url  =  "https://my-json-server.typicode.com/typicode/demo/db";
                String url = api.APIlogin;
                SharedPreferences.Editor edit = pref.edit();
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String message = jsonObject.getString("message");
                            if(status.equals("true")) {
                                JSONObject ids = jsonObject.getJSONObject("data");
                                String tkn = ids.getString("token");
                                String id = ids.getString("id");
                                edit.putString("token", tkn);
                                edit.putString("id", id);
                                edit.commit();
                                txtusername.setText("");
                                txtpass.setText("");

                                Intent intent = new Intent(Login.this, Dashboard.class);
                                startActivity(intent);

                                Toast.makeText(getApplicationContext(), "Selamat datang "+ message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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
                   protected Map<String, String>getParams(){
                        Map<String, String> kirim_form = new HashMap<String, String>();
                        kirim_form.put("username", txtusername.getText().toString());
                        kirim_form.put("password", txtpass.getText().toString());
                        return kirim_form;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
                requestQueue.add(request);
            }
        });
   }
}
