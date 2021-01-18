package com.israel.providum.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.israel.providum.Dashboard;
import com.israel.providum.PI.RetrofitFactory;
import com.israel.providum.PI.RetrofitServices;
import com.israel.providum.R;
import com.israel.providum.model.LoginResponse;
import com.israel.providum.sessionmanager.SessionManagerToken;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginScreen extends AppCompatActivity {
    private Button buttonlogin;
    private EditText editText1, editText2;
    private String access_token;
    SessionManagerToken sessionManager;
    ProgressBar progressBar;
    RetrofitServices apiInterface;
    private static String URL_LOGIN = "https://admin.myprovidum.com/api/controllers/admin/admin_login.php/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        //INSTANCE OF SHARED PREFERENCE / SESSION MANAGER
        sessionManager = new SessionManagerToken(this);
        if (sessionManager.isLoggin()) {
            finish();
            startActivity(new Intent(this, Dashboard.class));
        }


        buttonlogin = findViewById(R.id.button_login_main);
        editText1 = findViewById(R.id.editText_email_login);
        editText2 = findViewById(R.id.editText_password_login);
        progressBar = findViewById(R.id.progressBar);//progress bar

        // SET VALUE INSTANTLY TO LOGIN
        Intent intent = getIntent();
        String tokenpass = intent.getStringExtra("token");


        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editText1.getText().toString().trim();
                String password = editText2.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()) {
                    progressBar.setVisibility(View.VISIBLE);
                    Login(email, password);
                } else {
                    editText1.setError("Email is required");
                    editText1.requestFocus();
                    editText2.setError("Password required");
                    editText2.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                }
                if (password.length() < 6) {
                    editText2.setError("Password should be at least 6 character long");
                    editText2.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);

                }
            }
        });
    }


    private void Login(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(LoginScreen.this, "logged in", Toast.LENGTH_SHORT).show();
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginScreen.this, "please check internet connection", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<>();
//                headers.put("Content-Type", "application/json; charset=UTF-8");
//               headers.put("Authorization", "Bearer "+access_token);
//                return headers;
//            }
        };

        // getting an instance of singleton class
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}