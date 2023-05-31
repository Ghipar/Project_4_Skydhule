package com.example.project_4;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project_4.databinding.ActivityProfileBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;

public class Profile extends AppCompatActivity {
    Bitmap imageBitmap;
    SharedPreferences pref;

    LinearLayout cob;

    LinearLayout btnber;
    void getDat() {
        TextView username = (TextView)findViewById(R.id.username);
        TextView email = (TextView)findViewById(R.id.email);
        TextView phone= (TextView)findViewById(R.id.phone);
        TextView alamat = (TextView)findViewById(R.id.alamat);
        TextView nama = (TextView)findViewById(R.id.nama);
        LinearLayout btnlangg = (LinearLayout) findViewById(R.id.langgan);
        CircleImageView prof =(CircleImageView) findViewById(R.id.profile_image);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("TokenPrefs", Context.MODE_PRIVATE);
        API api = new API();
        String url = api.APIuser;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            private static final String TAG = "Profile";
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
                    String lvl = jsonObject.getString("status");

                    if(lvl.equals("3")){
                        prof.setBackground(getDrawable(R.drawable.propileprem));
                        btnlangg.setVisibility(View.GONE);
                    } else  {
                        prof.setBackground(getDrawable(R.drawable.propile2));
                        btnlangg.setVisibility(View.VISIBLE);
                    }

                    username.append(usernamedat);
                    email.append(emaildat);
                    phone.append(num);
                    nama.append(name);
                    alamat.append(almt);

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
        RequestQueue requestQueue = Volley.newRequestQueue(Profile.this);
        requestQueue.add(request);
    }

    void kiltok() {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("TokenPrefs", Context.MODE_PRIVATE);
        API api = new API();
        //String url  =  "https://my-json-server.typicode.com/typicode/demo/db";
        String url = api.APIlogout;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Berhasil logout", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String>getParams(){
                String tok = sp.getString("token","");
                HashMap header = new HashMap();
                header.put("Accept", "application/json");
                header.put("Authorization", "Bearer " + tok);
                return header;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Profile.this);
        requestQueue.add(request);
    }
    private final int GALLERY_REQ_CODE = 1000;
    ImageView profpic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ImageView edit = (ImageView)findViewById(R.id.backprof);
        ImageView back = (ImageView)findViewById(R.id.backdash);
        LinearLayout btnjdwl = (LinearLayout) findViewById(R.id.btnjdwl);
        LinearLayout btnber = (LinearLayout) findViewById(R.id.btnber);
        LinearLayout btnhistor = (LinearLayout) findViewById(R.id.histor);
        LinearLayout btnlangg = (LinearLayout) findViewById(R.id.langgan);

        btnlangg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, PremPage.class);
                startActivity(intent);
            }
        });

        btnhistor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, History.class);
                startActivity(intent);
            }
        });

        btnber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Berita.class);
                startActivity(intent);
            }
        });

        btnjdwl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, LibSchedule.class);
                startActivity(intent);
            }
        });

        cob = findViewById(R.id.cob);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, EditProf.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Dashboard.class);
                startActivity(intent);
            }
        });
        cob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kiltok();
                pref = getSharedPreferences("TokenPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(Profile.this, Login.class);
                startActivity(intent);
            }
        });
        getDat();
    }
}