package com.example.a19029489_c302_magic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CardActivity_19029489 extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<Card> adapter;
    private ArrayList<Card> list;
    private AsyncHttpClient client;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_19029489);

        listView = (ListView) findViewById(R.id.cardList);
        list = new ArrayList<Card>();
        adapter = new ArrayAdapter<Card>(this, android.R.layout.simple_list_item_1, list);
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

        Intent i = getIntent();
        int colourId = i.getIntExtra("colourId", 0);

        RequestParams params = new RequestParams();
        params.add("loginId", loginId);
        params.add("apikey", apikey);
        params.add("colourId", "" + colourId);

        //for real devices, use the current location's ip address
        client.post("http://10.0.2.2/19029489_C302_magic/19029489_getCardsByColour.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    Log.i("JSON Results: ", response.toString());

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject jsonObj = response.getJSONObject(i);

                        int cardId = jsonObj.getInt("cardId");
                        String name = jsonObj.getString("cardName");
                        int colourId = jsonObj.getInt("colourId");
                        int typeId = jsonObj.getInt("typeId");
                        double price = jsonObj.getDouble("price");
                        int quantity = jsonObj.getInt("quantity");

                        Card card = new Card(cardId, name, colourId, typeId, price, quantity);
                        list.add(card);
                    }

                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }//end onSuccess
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

            Intent i = new Intent(CardActivity_19029489.this, LoginActivity.class);
            startActivity(i);

            return true;

        } else if (id == R.id.menu_add) {

            Intent i = new Intent(CardActivity_19029489.this, CreateCardActivity_19029489.class);
            startActivity(i);

        } else if (id == R.id.menu_colour) {

            Intent i = new Intent(CardActivity_19029489.this, MainActivity.class);
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }
}