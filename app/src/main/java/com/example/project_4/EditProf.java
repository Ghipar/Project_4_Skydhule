package com.example.project_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class EditProf extends AppCompatActivity {

    void getDat() {
        TextView username = (TextView)findViewById(R.id.txtusername);
        TextView email = (TextView)findViewById(R.id.txtemail);
        TextView phone= (TextView)findViewById(R.id.txtphone);
        TextView alamat = (TextView)findViewById(R.id.txtalamat);
        TextView nama = (TextView)findViewById(R.id.txtnama);
        TextView idnya = (TextView)findViewById(R.id.idnya);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("TokenPrefs", Context.MODE_PRIVATE);
        API api = new API();
        String url = api.APIuser;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
//                            String code = jsonObject.getString("code");
                    String usernamedat = jsonObject.getString("username");
                    String emaildat = jsonObject.getString("email");
                    String num = jsonObject.getString("phone");
                    String almt = jsonObject.getString("alamat");
                    String name = jsonObject.getString("nama");
                    String d = jsonObject.getString("id");

                    username.setText(usernamedat);
                    email.setText(emaildat);
                    phone.setText(num);
                    nama.setText(name);
                    alamat.setText(almt);
                    idnya.append(d);
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
        RequestQueue requestQueue = Volley.newRequestQueue(EditProf.this);
        requestQueue.add(request);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_prof);
        ImageView back = (ImageView)findViewById(R.id.backprof);
        Button simpan = (Button) findViewById(R.id.simper);
        TextView idnya = (TextView)findViewById(R.id.idnya);
        TextView username = (TextView)findViewById(R.id.txtusername);
        TextView email = (TextView)findViewById(R.id.txtemail);
        TextView phone= (TextView)findViewById(R.id.txtphone);
        TextView alamat = (TextView)findViewById(R.id.txtalamat);
        TextView nama = (TextView)findViewById(R.id.txtnama);
        getDat();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProf.this, Profile.class);
                startActivity(intent);
            }

        });
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit();
            }

            void edit() {
                API api = new API();
                //String url  =  "https://my-json-server.typicode.com/typicode/demo/db";
                String url = api.APIupdate + "/" + idnya.getText();
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");
                            if(code.equals("200")) {
                                Intent intent = new Intent(EditProf.this, Profile.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Edit akun berhasil", Toast.LENGTH_SHORT).show();
                            } else  {
                                Toast.makeText(getApplicationContext(), "Edit gagal", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(username.getText().toString().isEmpty() || nama.getText().toString().isEmpty()
                        || alamat.getText().toString().isEmpty() || email.getText().toString().isEmpty() || phone.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Mohon lengkapi data", Toast.LENGTH_SHORT).show();
                        }  else {
                            Toast.makeText(getApplicationContext(), "Edit gagal!", Toast.LENGTH_SHORT).show();
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
                        kirim_form.put("username", username.getText().toString());
                        kirim_form.put("email", email.getText().toString());
                        kirim_form.put("nama", nama.getText().toString());
                        kirim_form.put("alamat", alamat.getText().toString());
                        kirim_form.put("phone", phone.getText().toString());
                        return kirim_form;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(EditProf.this);
                requestQueue.add(request);
            }
        });
    }
}