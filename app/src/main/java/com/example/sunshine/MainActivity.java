package com.example.sunshine;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunshine.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements WheatherAdapter.ListItemClickListener {
    TextView textView,error_text;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    WheatherAdapter wheatherAdapter;
    SharedPreferences sharedPreferences;
    String geolocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview_forecast);
        error_text = findViewById(R.id.tv_error_message_display);
        progressBar = findViewById(R.id.pb_loading_indicator);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        wheatherAdapter = new WheatherAdapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(wheatherAdapter);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        geolocation = sharedPreferences.getString("location","delhi");
//
        FetchWeatherTask fetchWeatherTask = new FetchWeatherTask();
        fetchWeatherTask.execute(geolocation);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.refresh){
            wheatherAdapter.setWeatherData(null);

            FetchWeatherTask fetchWeatherTask = new FetchWeatherTask();
            fetchWeatherTask.execute(geolocation);
            return true;
        }
        if(id==R.id.action_map){
            openLocationInMap();
            return true;
        }
        if(id == R.id.action_settings){
            Intent intent = new Intent(this,Setting.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    public String[] LoadWeatherData(String json) {
        String [] data = null;
        try {
            JSONObject root = new JSONObject(json);
            JSONArray list = root.getJSONArray("list");
            data = new String[list.length()];
            for(int i = 0;i<list.length();i++){
                JSONObject element = list.getJSONObject(i);
                JSONArray weather = element.getJSONArray("weather");
                JSONObject main_data = weather.getJSONObject(0);
                String main = main_data.getString("main");
                String description = main_data.getString("description");
                String unit = sharedPreferences.getString("units","Celsius");

                String all_data = main + " " + description+" "+unit;
                data[i] = all_data;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
    private void openLocationInMap() {
        String addressString = "1600 Ampitheatre Parkway, CA";
        Uri geoLocation = Uri.parse("geo:0,0?q=" + addressString);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("open_map", "Couldn't call " + geoLocation.toString()
                    + ", no receiving apps installed!");
        }
    }
    private void showWeatherDataView() {
        /* First, make sure the error is invisible */
        error_text.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        recyclerView.setVisibility(View.VISIBLE);
    }
    private void showErrorMessage() {
        /* First, hide the currently visible data */
        recyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        error_text.setVisibility(View.VISIBLE);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        String[] data = WheatherAdapter.getData();
        Intent intent = new Intent(MainActivity.this,DetailActivity.class);
        intent.putExtra("data",data[clickedItemIndex]);
        startActivity(intent);
    }



    public class FetchWeatherTask extends AsyncTask<String,Void,String[]> {

        @Override
        protected String[] doInBackground(String... strings) {
            URL url = NetworkUtils.buildUrl(strings[0]);
            String json = "";
            try {
                json = NetworkUtils.getHttpResponse(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return LoadWeatherData(json);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String[] data) {
            progressBar.setVisibility(View.INVISIBLE);
            if(data!=null){
                showWeatherDataView();
                wheatherAdapter.setWeatherData(data);
            }else{
                Toast.makeText(MainActivity.this, "data is null", Toast.LENGTH_SHORT).show();
                showErrorMessage();
            }
        }
    }

}