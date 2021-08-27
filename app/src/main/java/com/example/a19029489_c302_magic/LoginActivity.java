package com.example.a19029489_c302_magic;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    private EditText etLoginID, etPassword;
    private Button btnSubmit;

    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginID = (EditText)findViewById(R.id.editTextLoginID);
        etPassword = (EditText)findViewById(R.id.editTextPassword);
        btnSubmit = (Button)findViewById(R.id.buttonSubmit);

        client = new AsyncHttpClient();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etLoginID.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.equalsIgnoreCase("")) {
                    Toast.makeText(LoginActivity.this, "Login failed. Please enter username.", Toast.LENGTH_LONG).show();

                } else if (password.equalsIgnoreCase("")) {
                    Toast.makeText(LoginActivity.this, "Login failed. Please enter password.", Toast.LENGTH_LONG).show();

                } else {
                    OnLogin(v);
                }
            }
        });
    }

    private void OnLogin(View v) {

        RequestParams params = new RequestParams();
        params.add("username", etLoginID.getText().toString());
        params.add("password", etPassword.getText().toString());


        client.post("http://10.0.2.2/19029489_C302_magic/doLogin.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    Log.i("JSON Results: ", response.toString());

                    Boolean authenticated = response.getBoolean("authenticated");

                    if (authenticated == true) {
                        String apikey = response.getString("apikey");
                        String loginId = response.getString("id");
                        String role = response.getString("role");

                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("loginId", loginId);
                        editor.putString("role", role);
                        editor.putString("apikey", apikey);
                        editor.commit();


                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);

                    } else {
                        Toast.makeText(getApplicationContext(), "Login failed. Please check your login credentials.", Toast.LENGTH_SHORT).show();
                    }

                }
                catch (JSONException e){
                    e.printStackTrace();
                }

            }
        });

    }
}
