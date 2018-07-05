package com.example.android.quakereport;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RetrieveEarthquake extends AsyncTask<Void, Void, String> {
    /*
    Retrieves the data from the usgs API instead of using the placeholder JSON file provided
    in the udacity tutorial
     */

    //the url that contains the usgs earthquake information
    private String stringUrl = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02";
    //our main activity which implements the interface and where the data will be used
    FetchDataCallbackInterface callbackInterface;

    RetrieveEarthquake(FetchDataCallbackInterface callback){
        callbackInterface = callback;
    }

    protected String doInBackground(Void... urls){

        try {
            //open a connection with the website
            URL queryUrl = new URL(stringUrl);
            HttpURLConnection usgsConnection = (HttpURLConnection) queryUrl.openConnection();
            try{

                //variables to read in the data and store it
                BufferedReader jsonReader = new BufferedReader(new InputStreamReader(usgsConnection.getInputStream()));
                StringBuilder jsonBuilder= new StringBuilder();

                String eqData;

                //reads in each line of json data and adds it to the jsonBuilder
                while((eqData = jsonReader.readLine()) != null){
                    jsonBuilder.append(eqData).append('\n');
                }
                jsonReader.close();

                //returns the completed string
                return jsonBuilder.toString();

            }finally{
                //closes the connection
                usgsConnection.disconnect();
            }

        } catch (Exception e){
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }

    }

    protected void onPostExecute(String usgsJSON){
        //send the data back to the main activit
        this.callbackInterface.fetchCallback(usgsJSON);
    }
}
