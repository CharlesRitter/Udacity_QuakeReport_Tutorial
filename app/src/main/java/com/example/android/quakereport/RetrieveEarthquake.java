package com.example.android.quakereport;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RetrieveEarthquake extends AsyncTask<Void, Void, String> {
    /*
    Retrieves the data from the usgs API instead of using the placeholder JSON file provided
    in the udacity tutorial
     */

    protected String doInBackground(Void... urls){

        try {
            URL queryUrl = new URL("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02");
            HttpURLConnection usgsConnection = (HttpURLConnection) queryUrl.openConnection();
            try{

                BufferedReader jsonReader = new BufferedReader(new InputStreamReader(usgsConnection.getInputStream()));
                StringBuilder jsonBuilder= new StringBuilder();

                String eqData;

                while((eqData = jsonReader.readLine()) != null){
                    jsonBuilder.append(eqData).append('\n');
                }
                jsonReader.close();
                return jsonBuilder.toString();

            }finally{
                usgsConnection.disconnect();
            }

        } catch (Exception e){
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }

    }
}
