package com.example.android.quakereport;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class EarthquakeModel extends ViewModel {
    private static MutableLiveData<List<EarthquakeClass>> earthquakes;

    /**
     * Returns a list of earthquakes retrieved from the url if it exists and retrieves them if
     * the list does not.
     * @param url the url to retrieve the earthquakes from
     * @return a list of earthquakes retrieved from the url
     */
    public LiveData<List<EarthquakeClass>> getEarthquakes(String url){
        if(earthquakes == null){
            earthquakes = new MutableLiveData<>();
            loadQuakes(url);
        }

        return earthquakes;
    }

    /**
     * Asynchronous task that retrieves the earthquakes from url
     * @param url the location of the json file containing the earthquakes
     */
    private void loadQuakes(String url){
        new RetrieveEarthquake().execute(url);
    }

    /**
     * The asynchronous task which pulls the earthquakes from the usgs api
     */
    public static class RetrieveEarthquake extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls){

            try {
                //check to see if the function was called correctly
                if(urls.length != 1){
                    return null;
                }

                //open a connection with the website
                URL queryUrl = new URL(urls[0]);
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
            //do nothing if doInBackground returned null
            if(usgsJSON == null){
                return;
            }

            earthquakes.setValue(QueryUtils.extractEarthquakes(usgsJSON));
        }
    }

}
