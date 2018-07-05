package com.example.android.quakereport;

/**
 * This is an interface which that the main activity implements in order to move the data from
 * the RetrieveEarthquake class to the main class
 */
public interface FetchDataCallbackInterface {
    public void fetchCallback (String result);
}
