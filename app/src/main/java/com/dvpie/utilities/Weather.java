package com.dvpie.utilities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dvpie.utilities.networking.UtilityNetworking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Weather extends AppCompatActivity {
    private LocationData location;
    private WeatherData weather;
    private TextView currentTemp, todaysHigh,todaysLow,precipTxt,visibility,humidity;
    private TextView[] forecastTitles = new TextView[4],forecastHighs = new TextView[4],forecastLows = new TextView[4];
    private ImageView[] forecastIcons = new ImageView[4];
    //TODO: If/While there is no internet connection or response, display either loading screen or no internet
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Get TextViews
        currentTemp = findViewById(R.id.currentTemp);
        todaysHigh = findViewById(R.id.todays_highTemp);
        todaysLow = findViewById(R.id.todays_lowTemp);
        precipTxt = findViewById(R.id.precipitation);
        visibility = findViewById(R.id.visibility);
        humidity = findViewById(R.id.humidity);

        forecastTitles[0] = findViewById(R.id.forecast_title_1);
        forecastTitles[1] = findViewById(R.id.forecast_title_2);
        forecastTitles[2] = findViewById(R.id.forecast_title_3);
        forecastTitles[3] = findViewById(R.id.forecast_title_4);

        forecastHighs[0] = findViewById(R.id.forecast_hi_1);
        forecastHighs[1] = findViewById(R.id.forecast_hi_2);
        forecastHighs[2] = findViewById(R.id.forecast_hi_3);
        forecastHighs[3] = findViewById(R.id.forecast_hi_4);

        forecastLows[0] = findViewById(R.id.forecast_lo_1);
        forecastLows[1] = findViewById(R.id.forecast_lo_2);
        forecastLows[2] = findViewById(R.id.forecast_lo_3);
        forecastLows[3] = findViewById(R.id.forecast_lo_4);

        forecastIcons[0] = findViewById(R.id.forecast_summary_1);
        forecastIcons[1] = findViewById(R.id.forecast_summary_2);
        forecastIcons[2] = findViewById(R.id.forecast_summary_3);
        forecastIcons[3] = findViewById(R.id.forecast_summary_4);

        precipTxt.setPaintFlags(0);
        //Get intent info


        Context mContext = getApplicationContext();


        boolean internet = UtilityNetworking.isNetworkAvailable((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));
        if (internet){
            location = new LocationData(mContext);
            System.out.println(location.toString());
            weather = new WeatherData(location);

            Thread INIT = new Thread() {
                public void run() {
                    populateView();
                }
            };
            INIT.start();
        } else {
            //No Internet Connection - Display No Internet
            LinearLayout ForecastLayout = findViewById(R.id.forecast_layout);
            LinearLayout CurrentWeatherLayout = findViewById(R.id.current_weather_layout);
            LinearLayout VisHumLayout = findViewById(R.id.vis_hum_layout);
            LinearLayout HighLows = findViewById(R.id.highlows);
            LinearLayout PrecipLayout = findViewById(R.id.precip_layout);

            ForecastLayout.setVisibility(View.GONE);
            CurrentWeatherLayout.setVisibility(View.GONE);
            VisHumLayout.setVisibility(View.GONE);
            HighLows.setVisibility(View.GONE);
            PrecipLayout.setVisibility(View.GONE);
            //Now We have to construct something that says no internet
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.no_internet)
                .setPositiveButton(R.string.reload,new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {
                        //Reload the intent
                        finish();
                        startActivity(getIntent());
                    }
                })
                .setNegativeButton(R.string.back,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Go back to main page
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }
    private String getDay(int t) {
        String day;
        switch(t){
            case 1:
                day = "Sunday";
                break;
            case 2:
                day = "Monday";
                break;
            case 3:
                day = "Tuesday";
                break;
            case 4:
                day = "Wednesday";
                break;
            case 5:
                day = "Thursday";
                break;
            case 6:
                day = "Friday";
                break;
            case 7:
                day = "Saturday";
                break;
            default:
                day = "Saturday";
                break;
        }
        return day;
    }
    private void populateView() {
        Looper.prepare();
        try {
            while(!weather.isReady()) {
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        weather.setUpForecast();

        //Calendar.getInstance(TimeZone.getTimeZone("EST"));
        Calendar tdy = Calendar.getInstance(Locale.US);
        String today = tdy.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US);








        String degrees = "°";
        String cTemp = (int)weather.getCurrentTemp() + degrees;
        String tHigh = (int)weather.getTodayHigh() + degrees;
        String tLow = (int)weather.getTodayLow() + degrees;
        String pText = constructPrecipitationText();
        String vText = "Visibility: ";
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.FLOOR);
        if(weather.getCurrentVisibility() <= weather.getTodaysVisibility()) {
            vText += df.format(weather.getCurrentVisibility());
        } else {
            vText += df.format(weather.getTodaysVisibility());
        }
        String hText = "Humidity: ";
        if(weather.getCurrentHumidity() >= weather.getTodaysHumidity()) {
            hText += weather.getCurrentHumidity() + "%";
        } else {
            hText += weather.getTodaysHumidity() + "%";
        }


        //Populate the Forecast Area
        for(int i = 0; i < 4; i++) {
            tdy.add(Calendar.DATE,1);
            String day = tdy.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.SHORT,Locale.US);
            String fHigh = "Hi: " + (int)(weather.getForecastDayHigh(i + 1)) + degrees;
            String fLow = "Lo: " + (int)(weather.getForecastDayLow(i+1)) + degrees;
            if(day.equals("Sun"))
                day = "Sunday";
            else if(day.equals("Mon"))
                day = "Monday";
            else if(day.equals("Tue"))
                day = "Tuesday";
            else if(day.equals("Wed"))
                day = "Wednesday";
            else if(day.equals("Thu"))
                day = "Thursday";
            else if(day.equals("Fri"))
                day = "Friday";
            else
                day = "Saturday";
            //Got icons from http://darkskyapp.github.io/skycons/
            final String icon = weather.getForecastDayIcon(i+1);

            final int count = i;
            Thread thread = new Thread()
            {
              @Override
              public void run() {
                  runOnUiThread(new Runnable()
                  {
                     public void run() {
                         if(icon.equals("clear-day")) {
                             forecastIcons[count].setImageResource(R.drawable.clear_day);
                         } else if(icon.equals("partly-cloudy-day")) {
                             forecastIcons[count].setImageResource(R.drawable.partly_cloudy_day);
                         } else if(icon.equals("rain")) {
                             forecastIcons[count].setImageResource(R.drawable.rain);
                         } else if(icon.equals("cloudy")) {
                             forecastIcons[count].setImageResource(R.drawable.cloudy);
                         } else if(icon.equals("snow")) {
                             forecastIcons[count].setImageResource(R.drawable.snow);
                         }
                     }
                  });
              }
            };
            thread.start();

            forecastTitles[i].setText(day);
            forecastHighs[i].setText(fHigh);
            forecastLows[i].setText(fLow);
        }



        precipTxt.setText(pText);
        visibility.setText(vText);
        humidity.setText(hText);
        todaysLow.setText(tLow);
        todaysHigh.setText(tHigh);
        currentTemp.setText(cTemp);
    }

    private String constructPrecipitationText() {
        String pText = "Currently: " + weather.getPrecipitationChance() + "%" + " chance of " + weather.getPrecipitationType();

        return pText;
    }

}
