package com.example.project_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    Button btnreg;
    EditText txtusername;
    EditText txtemail;
    EditText txtpass;
    EditText txtpasscon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnreg = findViewById(R.id.btnreg);
        txtusername = findViewById(R.id.txtusername);
        txtemail = findViewById(R.id.txtemail);
        txtpass = findViewById(R.id.txtpass);
        txtpasscon = findViewById(R.id.txtpasscon);
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtemail.getText().toString().trim().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(txtemail.getText().toString()).matches()) {
                    validateEmail();
                } else if(txtusername.getText().toString().trim().isEmpty()) {
                    txtusername.setError("Field can't be empty");
                }
                else {
                    kirimData();
                }
            }
            private boolean validateEmail() {
                String emInput = txtemail.getText().toString().trim();
                if (emInput.isEmpty()) {

                    txtemail.setError("Field can't be empty");
                    return false;
                } else if(!Patterns.EMAIL_ADDRESS.matcher(emInput).matches()) {

                    txtemail.setError("Please enter a valid email address");
                    return false;
                } else {
                    txtemail.setError(null);
                    return true;
                }
            }
            void kirimData() {
                API api = new API();
                //String url  =  "https://my-json-server.typicode.com/typicode/demo/db";
                String url = api.APIReg;
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String message = jsonObject.getString("message");
                            if(status.equals("true")) {
                                txtemail.setText("");
                                txtusername.setText("");
                                txtpass.setText("");
                                txtpasscon.setText("");
                                Intent intent = new Intent(Register.this, Login.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Register berhasil", Toast.LENGTH_SHORT).show();
                            } else {
                                if(txtpass.getText().toString().isEmpty() || txtpasscon.getText().toString().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Mohon isi data password!", Toast.LENGTH_SHORT).show();
                                } else if (txtpasscon.getText().toString() != txtpass.getText().toString()) {
                                    Toast.makeText(getApplicationContext(), "Pastikan password sama!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Register gagal!", Toast.LENGTH_SHORT).show();
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
                        System.out.println(error.getMessage());
                    }
                }){
                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    protected Map<String, String> getParams(){
                        Map<String, String> kirim_form = new HashMap<String, String>();
                        kirim_form.put("username", txtusername.getText().toString());
                        kirim_form.put("email", txtemail.getText().toString());
                        kirim_form.put("password", txtpass.getText().toString());
                        kirim_form.put("confirm_password", txtpasscon.getText().toString());
                        kirim_form.put("level", "1");
                        kirim_form.put("status", "1");
                        kirim_form.put("joined", date);
                        kirim_form.put("nama", "Nama");
                        kirim_form.put("alamat", "Alamat");
                        kirim_form.put("phone", "Phone");
                        kirim_form.put("jmlh_kolom", "0");
                        return kirim_form;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
                requestQueue.add(request);
            }
        });
  }
}