package com.example.project_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class LupPass extends AppCompatActivity {
    Button btnconfirm;

    EditText txtemail;
    EditText txtpass;
    EditText txtpasscon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lup_pass);
        btnconfirm = findViewById(R.id.btnconfirm);
        txtemail = findViewById(R.id.txtemail);
        txtpass = findViewById(R.id.txtpass);
        txtpasscon = findViewById(R.id.txtpasscon);
        TextView idnya = (TextView)findViewById(R.id.ids);
        txtpass.setFocusable(false);
        txtpasscon.setFocusable(false);
        ImageView back = (ImageView) findViewById(R.id.btnbackfpas);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LupPass.this, Login.class);
                startActivity(intent);
            }
        });
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idnya.getText().toString().isEmpty()) {
                    verifEmail();
                    System.out.println("ksong");
                }else {
                    fPass();
                    System.out.println("ada");
                }
            }
            void verifEmail() {
                API api = new API();
                //String url  =  "https://my-json-server.typicode.com/typicode/demo/db";
                String url = api.APIVerEml;
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String message = jsonObject.getString("message");
                            if(status.equals("true")) {
                                JSONObject ids = jsonObject.getJSONObject("data");
                                String id = ids.getString("id");
                                idnya.setText(id);
                                txtemail.setText("");
                                txtemail.setFocusable(false);
                                txtemail.setBackgroundResource(R.drawable.custom_input2);
                                txtpass.setFocusableInTouchMode(true);
                                txtpasscon.setFocusableInTouchMode(true);
                                txtpass.setBackgroundResource(R.drawable.custom_input);
                                txtpasscon.setBackgroundResource(R.drawable.custom_input);
                                btnconfirm.setText("Confirm");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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
                    protected Map<String, String> getParams(){
                        Map<String, String> kirim_form = new HashMap<String, String>();
                        kirim_form.put("email", txtemail.getText().toString());
                        return kirim_form;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(LupPass.this);
                requestQueue.add(request);
            }

            void fPass() {
                API api = new API();
                //String url  =  "https://my-json-server.typicode.com/typicode/demo/db";
                String url = api.APIfPass + "/" + idnya.getText();
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");
                            if(code.equals("200")) {
                                txtpass.setText("");
                                txtpasscon.setText("");
                                Intent intent = new Intent(LupPass.this, Login.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Reset password berhasil", Toast.LENGTH_SHORT).show();
                            } else  {
                                JSONObject ids = jsonObject.getJSONObject("data");
                                String cp = ids.getString("confirm_password");
                                Toast.makeText(getApplicationContext(), cp, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(txtpass.getText().toString().isEmpty() || txtpasscon.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Mohon isi data password!", Toast.LENGTH_SHORT).show();
                        } else if (txtpasscon.getText().toString() != txtpass.getText().toString()) {
                            Toast.makeText(getApplicationContext(), "Pastikan password sama!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Register gagal!", Toast.LENGTH_SHORT).show();
                        }

                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap header = new HashMap();
                        header.put("Accept", "application/json");

                        return header;
                    }

                    protected Map<String, String> getParams(){
                        Map<String, String> kirim_form = new HashMap<String, String>();
                        kirim_form.put("password", txtpass.getText().toString());
                        kirim_form.put("confirm_pass", txtpasscon.getText().toString());
                        return kirim_form;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(LupPass.this);
                requestQueue.add(request);
            }
        });

    }
}