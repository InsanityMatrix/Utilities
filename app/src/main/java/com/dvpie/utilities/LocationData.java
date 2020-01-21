package com.dvpie.utilities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

public class LocationData {
    private LocationManager lManager;
    private Location currentLocation;


    public LocationData(Context mContext) {
        this.lManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        assert this.lManager != null;
        boolean isGpsEnabled = this.lManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkLocationEnabled = this.lManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location networkLocation = null, gpsLocation = null;
        if(isGpsEnabled) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(mContext, "Failed to retrieve Location",Toast.LENGTH_LONG);
                System.out.println("Failed to Retrieve Location");
                return;
            }
            gpsLocation = lManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if(isNetworkLocationEnabled) {
            networkLocation = lManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        if(gpsLocation != null && networkLocation != null) {
            /*
            if(gpsLocation.getAccuracy() > networkLocation.getAccuracy()) {
                this.currentLocation = networkLocation;
            } else {
                this.currentLocation = gpsLocation;
            }
            */
            this.currentLocation = gpsLocation;
        } else {
            if(gpsLocation != null) {
                this.currentLocation = gpsLocation;
            }/* else if(networkLocation != null) {
                this.currentLocation = networkLocation;
            }*/ else {
                double[] loc = getIPLocation();
                currentLocation = new Location("");
                currentLocation.setLatitude(loc[0]);
                currentLocation.setLongitude(loc[1]);

                Toast.makeText(mContext,"Location could not be retreived.", Toast.LENGTH_LONG).show();
            }
        }

    }

    private double[] getIPLocation() {
        double[] loc = {0, 0};
        try {
            URL urlCon = new URL("https://www.ipapi.co/json");
            URLConnection request = urlCon.openConnection();
            request.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));

            String JSON = "";
            String inputLine;
            if((inputLine = br.readLine()) != null) {
                JSON += inputLine +"\n";
            } else {
                System.out.println("It was Null");
            }
            while((inputLine = br.readLine()) != null) {
                JSON += inputLine + "\n";

            }
            JSONObject resp = new JSONObject(JSON);
            double latitude = resp.getDouble("latitude");
            double longitude = resp.getDouble("longitude");
            loc[0] = latitude;
            loc[1] = longitude;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return loc;
    }

    public double getLat() {
        return this.currentLocation.getLatitude();
    }
    public double getLong() {
        return this.currentLocation.getLongitude();
    }
    public long getTime() {
        return this.currentLocation.getTime();
    }
    public float getSpeed() {
        return this.currentLocation.getSpeed();
    }
    public float getBearing() {
        return this.currentLocation.getBearing();
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.#####");
        df.setRoundingMode(RoundingMode.CEILING);
        String loc = df.format(this.getLat()) + ",";
        loc += df.format(this.getLong());
        return loc;
    }

}
