package com.dvpie.utilities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
//TODO: Implement Chatbot Page or should I implement it on main page?
public class MainActivity extends AppCompatActivity {
    final int LOCATION_REQUEST_CODE = 20;
    final int INTERNET_REQUEST_CODE = 21;
    final int EXTERNAL_FILES_CODE = 22;
    Context fContext;
    LocationData location;
    WeatherData weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the application Context for all future uses
        Context mContext = getApplicationContext();
        fContext = getApplicationContext();
        //Check if we need location permissions
        boolean fineLocation = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        boolean coarseLocation = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        if(fineLocation && coarseLocation) {
            //Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},LOCATION_REQUEST_CODE);

        }
        boolean internetAccess = ContextCompat.checkSelfPermission(mContext,Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED;
        boolean netAccess = ContextCompat.checkSelfPermission(mContext,Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED;
        if(internetAccess && netAccess) {
            //We need internet and Net access
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,Manifest.permission.ACCESS_NETWORK_STATE},INTERNET_REQUEST_CODE);
        }

        boolean read_external = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
        boolean write_external = ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
        if(write_external && read_external) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},EXTERNAL_FILES_CODE);
        }
        location = new LocationData(mContext);
        weather = new WeatherData(location);
        TextView WeatherLink = findViewById(R.id.weather_link);
        WeatherLink.setText(weather.getLink());
        TextView warningTXT = findViewById(R.id.warning);
        warningTXT.setVisibility(View.GONE);
        Thread wMess = new Thread() {
            @Override
            public void run() {
                setWarningMessage();
            }
        };
        wMess.start();



    }

    public void setWarningMessage() {
        try {
            while(!weather.isReady()) {
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int stormDistance = weather.getNearestStormDistance();
        if(stormDistance <= 15) {
            final String warning = "Warning: There is a storm " + stormDistance + " miles away";
            runOnUiThread(new Runnable() {
                public void run() {
                    TextView warningTXT = findViewById(R.id.warning);
                    warningTXT.setText(warning);
                    warningTXT.setVisibility(View.VISIBLE);
                }
            });

        } else {
            runOnUiThread(new Runnable() {
                public void run() {
                    TextView warningTXT = findViewById(R.id.warning);
                    warningTXT.setVisibility(View.GONE);
                }
            });

        }
    }

    public void switchToWeather(View view) {
        Intent intent = new Intent(this, Weather.class);
        intent.putExtra("WEATHER_URL", weather.fullURL);
        startActivity(intent);
    }
    public void switchToNews(View view) {
        Intent intent = new Intent(this, News.class);
        startActivity(intent);
    }
    public void switchToCalc(View view) {
        startActivity(new Intent(this, AlgebraicCalculator.class));
    }
    @Override
    public void onRequestPermissionsResult(int reqCode, String[] permissions, int[] grantResults) {
        switch(reqCode) {
            case LOCATION_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission Granted!
                    location = new LocationData(fContext);
                    weather = new WeatherData(location);
                    Toast.makeText(fContext,"Granted Location Permissions!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(fContext, "Location was not granted", Toast.LENGTH_LONG).show();
                }
                break;
            case INTERNET_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    location = new LocationData(fContext);
                    weather = new WeatherData(location);
                    Toast.makeText(fContext, "Internet Permission Granted!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(fContext, "Internet Permission Denied", Toast.LENGTH_LONG).show();
                }
                break;
            case EXTERNAL_FILES_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(fContext, "External File Permission Granted!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(fContext, "External File Permission Denied!", Toast.LENGTH_LONG).show();
                }
        }
    }


    public void switchToChem(View view) {
        Intent intent = new Intent(this, ChemistryTools.class);
        startActivity(intent);
    }
}
