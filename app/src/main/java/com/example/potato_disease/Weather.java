package com.example.potato_disease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarInputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Weather extends AppCompatActivity {
RecyclerView recyclerView;
ArrayList<Forecast> list=new ArrayList<>();
RecyclerViewAdapter recyclerViewAdapter;
private String city_name;
private String max_temp;
private String min_temp;
private String current_temp;
private String Humidity;




private String api="f374c04af4f942cc8a0184808231204";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        getSupportActionBar().hide();
        Intent intent = getIntent();

        Double l=intent.getDoubleExtra("latitude",'0');
        Double lo=intent.getDoubleExtra("longitude",'0');
        String lat=intent.getStringExtra("latitude");
        String longi=intent.getStringExtra("longitude");

        Log.d("latlongi",l+"  "+lo);
//         RecycleView
        recyclerView=findViewById(R.id.forecast_rv);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        RecyclerView Adapter

//        RecyclerViewAdapter recyclerViewAdapter=new RecyclerViewAdapter(Weather.this,list);
//        recyclerView.setAdapter(recyclerViewAdapter);


//        Retrofit

        Retrofit retrofit=new Retrofit.Builder()
                            .baseUrl("https://api.weatherapi.com/v1/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();


        MyApi myApi=retrofit.create(MyApi.class);



        myApi.getCurrentWeather(api, l.toString()+','+lo.toString(), 5, "no", "no", "en").enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                JsonObject forecast = response.body();
//                String temp=forecast.getAsJsonObject("current").get("temp_c").getAsString();
                int isDay=forecast.getAsJsonObject("current").get("is_day").getAsInt();
                String condition=forecast.getAsJsonObject("current").getAsJsonObject("condition").get("text").getAsString();
                String icon=forecast.getAsJsonObject("current").getAsJsonObject("condition").get("icon").getAsString();
                String humidity=forecast.getAsJsonObject("current").get("humidity").getAsString();
                Log.d("response_gautam",condition);
                Log.d("response_gautam",icon);
                Log.d("response_gautam",humidity);

                JsonObject fcast=forecast.getAsJsonObject("forecast");
                JsonArray farrya=fcast.getAsJsonArray("forecastday");
                System.out.println("farrya.size() " + farrya.size());
//
                for(int i=0;i<farrya.size();i++)
                {
                    JsonObject daily=farrya.get(i).getAsJsonObject();
                    JsonObject day=daily.getAsJsonObject("day");
                    String max_temp=day.get("maxtemp_c").getAsString();
                    String min_temp=day.get("mintemp_c").getAsString();;
                    String day_icon=day.getAsJsonObject("condition").get("icon").getAsString();
                    String Date=daily.get("date").getAsString();


                    Log.d("response_gautam1",max_temp);
                    Log.d("response_gautam2",min_temp);
                    Log.d("response_gautam3",day_icon);
                    Log.d("response_gautam4",Date);


                    list.add(new Forecast(Date,day_icon,max_temp,min_temp));
                }


                Log.d("msg_gautam","successful response body"+list.size());
//                recyclerViewAdapter.notifyDataSetChanged();



            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("error","An error occur during API CALL --> "+t);
            }
        });




    }


}