package com.vishal.weatherapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//https://api.weatherapi.com/v1/forecast.json?key=5bd38c2a010640febb064656221912&q=India
//   bde7bcbfad3de8cc173da59f0aa6c7eb api key
public class MainActivity extends AppCompatActivity {
EditText enterCity;
Button btnFind;
TextView cityName,temp;
RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enterCity =findViewById(R.id.enterData);
        btnFind =findViewById(R.id.btnFind);
        cityName =findViewById(R.id.city);
        temp =findViewById(R.id.temp);


        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTemperature(enterCity.getText().toString());
               // Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTemperature(String city) {
        String url="https://api.weatherapi.com/v1/forecast.json?key=5bd38c2a010640febb064656221912&q="+city;
        queue = Volley.newRequestQueue(this);
        JsonObjectRequest request =new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject =response.getJSONObject("location");
                    String cityN=jsonObject.getString("name");
//                    cityName.setText(+"City Name -:  "+cityN);
                    JSONObject jsonObject1 =response.getJSONObject("current");
                    String temperature =jsonObject1.getString("temp_c");
                    temp.setText("Temperature\n"+temperature+" °C");
                    JSONObject jForcast =response.getJSONObject("forecast");
                    JSONArray jsonArray =jForcast.getJSONArray("forecastday");
                    JSONObject jsonObject2 =jsonArray.getJSONObject(0);
                    String date=jsonObject2.getString("date");
                    cityName.setText("date - : "+date+"\nCity Name -:  "+cityN);

                } catch (JSONException e) {
                //    Toast.makeText(MainActivity.this, "enter correct/something", Toast.LENGTH_SHORT).show();
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);

    }
}