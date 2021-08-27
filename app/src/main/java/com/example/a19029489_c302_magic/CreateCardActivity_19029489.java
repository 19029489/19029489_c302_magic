package com.example.a19029489_c302_magic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

public class CreateCardActivity_19029489 extends AppCompatActivity {

    private EditText name, colourId, typeId, price, quantity;
    private Button btnAdd;
    private AsyncHttpClient client;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card_19029489);

        name = findViewById(R.id.editTextName);
        colourId = findViewById(R.id.editTextColourID);
        typeId = findViewById(R.id.editTextTypeID);
        price = findViewById(R.id.editTextPrice);
        quantity = findViewById(R.id.editTextQuantity);

        btnAdd = findViewById(R.id.buttonAdd);

        client = new AsyncHttpClient();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String loginId = prefs.getString("loginId", "");
        String apikey = prefs.getString("apikey", "");
        role = prefs.getString("role", "");


        if (loginId.equalsIgnoreCase("") || apikey.equalsIgnoreCase("") || role.equalsIgnoreCase("")) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equalsIgnoreCase("") || colourId.getText().toString().equalsIgnoreCase("") || typeId.getText().toString().equalsIgnoreCase("") || price.getText().toString().equalsIgnoreCase("") || quantity.getText().toString().equalsIgnoreCase("")) {
                    if (name.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(CreateCardActivity_19029489.this, "Name cannot be blank", Toast.LENGTH_SHORT).show();

                    } else if (colourId.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(CreateCardActivity_19029489.this, "Colour ID cannot be blank", Toast.LENGTH_SHORT).show();

                    } else if (typeId.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(CreateCardActivity_19029489.this, "Type ID cannot be blank", Toast.LENGTH_SHORT).show();

                    } else if (price.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(CreateCardActivity_19029489.this, "Price cannot be blank", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CreateCardActivity_19029489.this, "Quantity cannot be blank", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (Integer.parseInt(colourId.getText().toString()) > 5 || Integer.parseInt(colourId.getText().toString()) < 1 ) {
                        Toast.makeText(CreateCardActivity_19029489.this, "Colour ID must be 1 to 5", Toast.LENGTH_SHORT).show();

                    } else if (Integer.parseInt(typeId.getText().toString()) > 4 || Integer.parseInt(typeId.getText().toString()) < 1 ) {
                        Toast.makeText(CreateCardActivity_19029489.this, "Type ID must be 1 to 4", Toast.LENGTH_SHORT).show();

                    } else if (Double.parseDouble(price.getText().toString()) < 0.0) {
                        Toast.makeText(CreateCardActivity_19029489.this, "Price must be 0.0 or higher", Toast.LENGTH_SHORT).show();

                    } else if (Integer.parseInt(quantity.getText().toString()) < 0 ) {
                        Toast.makeText(CreateCardActivity_19029489.this, "Quantity must be 0 or higher", Toast.LENGTH_SHORT).show();
                    } else {
                        createCard(v);
                    }
                }
            }
        });

    }

    private void createCard(View v) {

        String cardName = name.getText().toString();
        String colourID = colourId.getText().toString();
        String typeID = typeId.getText().toString();
        String itemPrice = price.getText().toString();
        String itemQuantity = quantity.getText().toString();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String loginId = prefs.getString("loginId", "");
        String apikey = prefs.getString("apikey", "");

        RequestParams params = new RequestParams();
        params.add("loginId", loginId);
        params.add("apikey", apikey);
        params.add("name", cardName);
        params.add("colourId", colourID);
        params.add("typeId", typeID);
        params.add("price", itemPrice);
        params.add("quantity", itemQuantity);

        //for real devices, use the current location's ip address
        client.post("http://10.0.2.2/19029489_C302_magic/19029489_createCard.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Toast.makeText(CreateCardActivity_19029489.this, "Card created successfully", Toast.LENGTH_LONG).show();

                Intent i = new Intent(CreateCardActivity_19029489.this, MainActivity.class);
                startActivity(i);

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

            Intent i = new Intent(CreateCardActivity_19029489.this, LoginActivity.class);
            startActivity(i);

            return true;

        } else if (id == R.id.menu_add) {

            Intent i = new Intent(CreateCardActivity_19029489.this, CreateCardActivity_19029489.class);
            startActivity(i);

        } else if (id == R.id.menu_colour) {

            Intent i = new Intent(CreateCardActivity_19029489.this, MainActivity.class);
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }
}