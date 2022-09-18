package com.example.sunshine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
    TextView textView;
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textView = findViewById(R.id.textView2);

        Intent intent = getIntent();
        Toast.makeText(this, ""+intent.getStringExtra("data"), Toast.LENGTH_SHORT).show();


        if (intent != null) {
            data = intent.getStringExtra("data");
            textView.setText(data);
        }
    }
    public Intent shareIntentData(){
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(data + FORECAST_SHARE_HASHTAG)
                .getIntent();
        return shareIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_location,menu);
        MenuItem menutItem = menu.findItem(R.id.action_share);
        menutItem.setIntent(shareIntentData());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share){

        }
        if(id==R.id.action_settings){
            Intent intent = new Intent(DetailActivity.this,Setting.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}