package com.dvpie.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WeatherData {
    private LocationData currentLocation;
    private JSONObject JSONresp;
    private String Link;
    private JSONObject currently, daily, today;
    private String dsAPI = "https://api.darksky.net/forecast/";
    private String dsKey = "33389a86a9ec3ec2c4ca057f1e4e8c80/";
    private String dsParams = "?exclude=minutely&units=auto";
    private boolean ready = false;
    private JSONObject[] forecast;

    public double currentTemp, todaysHigh,todaysLow;
    public String fullURL;

    /**
     *
     * @param loc The location information of your user or location
     * @return Sets up the api to retrieve your weather, you can only use the data once the thread it creates stops running so use the isReady() along with Threading to use data
     *
     */
    public WeatherData(LocationData loc) {
        this.currentLocation = loc;
        final String UrlConn = dsAPI + dsKey + this.currentLocation.toString() + dsParams;
        this.Link = UrlConn;
        Thread getJSON = new Thread(){
          public void run() {
              retrieveWeather(UrlConn);
          }
        };
        getJSON.start();
    }
    public WeatherData(String UrlConn, Context mContext) {
        this.fullURL = UrlConn;
        this.currentLocation = new LocationData(mContext);
        final String url = UrlConn;
        Thread getJSON = new Thread(){
            public void run() {
                retrieveWeather(url);
            }
        };
        getJSON.start();
    }
    /**
     *
     * @param UrlConn The URL in which it should connect to to retrieve weather information
     * @return Sets up the api to retrieve your weather, you can only use the data once the thread it creates stops running so use the isReady() along with Threading to use data
     *
     */
    private void retrieveWeather(String UrlConn) {
        try {
            ready = false;
            this.fullURL = UrlConn;
            URL urlCon = new URL(UrlConn);
            URLConnection request = urlCon.openConnection();
            request.connect();

            InputStreamReader is = new InputStreamReader(request.getInputStream());
            BufferedReader br = new BufferedReader(is);
            String JSON = "";
            String inputLine;
            if((inputLine = br.readLine()) != null) {
                JSON += inputLine +"\n";
            } else {
                System.out.println("It was null");
            }
            while((inputLine = br.readLine()) != null) {
                JSON += inputLine + "\n";
            }
            System.out.println(JSON);
            JSONresp = new JSONObject(JSON);
            currently = JSONresp.getJSONObject("currently");
            System.out.println(currently.toString());
            currentTemp = currently.getDouble("temperature");
            System.out.println("Current Temp: " + currently.getDouble("temperature"));
            daily = JSONresp.getJSONObject("daily");
            today = daily.getJSONArray("data").getJSONObject(0);
            ready = true;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return returns true when the weather data is ready to be accessed
     */
    public boolean isReady() {
        return ready;
    }
    public void setUpForecast() {
        this.forecast = getWeekForecastObjects();
    }
    private JSONObject[] getWeekForecastObjects() {
        JSONObject[] weekForecast = new JSONObject[8];
        try{
            JSONArray data = daily.getJSONArray("data");

            for(int i = 0; i < 8; i++) {
                weekForecast[i] = data.getJSONObject(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return weekForecast;
    }
    public double getCurrentTemp() {
        try {
            System.out.println(currently.getDouble("temperature"));
            return currently.getDouble("temperature");
        } catch(Exception e) {
            e.printStackTrace();
            return 999;
        }
    }
    public String getLink() {
        return this.Link;
    }
    private double getHigh(JSONObject object) {
        try {
            return object.getDouble("temperatureHigh");
        } catch(Exception e) {
            return 999;
        }
    }
    private double getLow(JSONObject object) {
        try {
            return object.getDouble("temperatureLow");
        } catch(Exception e) {
            return 999;
        }
    }
    private String getIcon(JSONObject obj) {
        try{
            return obj.getString("icon");
        } catch(Exception e) {
            return "clear-day";
        }
    }
    public String getForecastDayIcon(int day) {
        return getIcon(forecast[day]);
    }
    public double getForecastDayHigh(int day) {
        return getHigh(forecast[day]);
    }
    public double getForecastDayLow(int day) {
        return getLow(forecast[day]);
    }
    public double getTodayHigh() {
        try {
            return this.today.getDouble("temperatureHigh");
        } catch(Exception e) {
            return 999;
        }
    }

    /**
     *
     * @return gets today's Low
     */
    public double getTodayLow() {
        try {
            return this.today.getDouble("temperatureLow");
        } catch(Exception e) {
            return 999;
        }
    }

    /**
     *
     * @return Gets current summary
     */
    public String getSummary() {
        try {
            return this.currently.getString("summary");
        } catch(Exception e) {
            return null;
        }
    }

    /**
     *
     * @return Gets current most likely precipitation type
     */
    public String getPrecipitationType() {
        try {
            String type = this.currently.getString("precipType");
            if(type == null) {
                type = "rain";
            }
            type = type.substring(0,1).toLowerCase() + type.substring(1);
            return type;

        } catch(Exception e) {
            return "Rain";
        }
    }
    public int getNearestStormDistance() {
        try {
            return (this.currently.getInt("nearestStormDistance"));
        } catch(Exception e) {
            return 900;
        }
    }

    /**
     *
     * @return Gets the CURRENT precipitation chance
     */
    public int getPrecipitationChance() {
        try {
            return (int) (this.currently.getDouble("precipProbability") * 100);
        } catch(Exception e) {
            return 0;
        }
    }

    /**
     *
     * @return Gets current precipitation intensity
     */
    public int getPrecipitationIntensity() {
        try {
            return (int) currently.getInt("precipIntensity");
        } catch(Exception e) {
            return 0;
        }
    }

    private int getHumidity(JSONObject object) {

        try {
            return (int)(object.getDouble("humidity") * 100);
        } catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private double getVisibility(JSONObject object) {
        try {
            return (object.getDouble("visibility"));
        } catch(Exception e) {
            e.printStackTrace();
            return 10;
        }
    }

    /**
     *
     * @return Gets Today's Time
     */
    public long getTodaysTime() {
        try {
            return today.getLong("time");
        } catch (Exception e) {
            return 1;
        }
    }


    /**
     *
     * @return Gets current Humidity
     */
    public int getCurrentHumidity() {
        return getHumidity(currently);
    }

    /**
     *
     * @return Gets today's Humidity
     */
    public int getTodaysHumidity() {
        return getHumidity(today);
    }

    /**
     *
     * @return Gets the current Visibility
     */
    public double getCurrentVisibility() {
        return getVisibility(currently);
    }

    /**
     *
     * @return Gets the Visibility for Today
     */
    public double getTodaysVisibility() {
        return getVisibility(today);
    }

}
