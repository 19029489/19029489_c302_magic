package com.example.a19029489_c302_magic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<Colour> adapter;
    private ArrayList<Colour> list;
    private AsyncHttpClient client;
    private String role;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.colourList);
        list = new ArrayList<Colour>();
        adapter = new ArrayAdapter<Colour>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        client = new AsyncHttpClient();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String loginId = prefs.getString("loginId", "");
        String apikey = prefs.getString("apikey", "");
        role = prefs.getString("role", "");


        if (loginId.equalsIgnoreCase("") || apikey.equalsIgnoreCase("") || role.equalsIgnoreCase("")) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        RequestParams params = new RequestParams();
        params.add("loginId", loginId);
        params.add("apikey", apikey);

        //for real devices, use the current location's ip address
        client.post("http://10.0.2.2/19029489_C302_magic/19029489_getColours.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    Log.i("JSON Results: ", response.toString());

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject jsonObj = response.getJSONObject(i);

                        int colourId = jsonObj.getInt("colourId");
                        String name = jsonObj.getString("colourName");

                        Colour colour = new Colour(colourId, name);
                        list.add(colour);
                    }

                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }//end onSuccess
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Colour selected = list.get(position);

                Intent i = new Intent(MainActivity.this, CardActivity_19029489.class);
                i.putExtra("colourId", "" + selected.getColourId());
                startActivity(i);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (!role.equalsIgnoreCase("")) {
            if (role.equalsIgnoreCase("customer")) {
                getMenuInflater().inflate(R.menu.menu_user, menu);
            } else {
                getMenuInflater().inflate(R.menu.menu_admin, menu);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_logout) {

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.commit();

            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);

            return true;

        } else if (id == R.id.menu_add) {

            Intent i = new Intent(MainActivity.this, CreateCardActivity_19029489.class);
            startActivity(i);

        } else if (id == R.id.menu_colour) {

            Intent i = new Intent(MainActivity.this, MainActivity.class);
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }
}