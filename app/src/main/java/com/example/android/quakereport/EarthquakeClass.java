package com.example.android.quakereport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EarthquakeClass {
    private String loc;
    private Double mag;
    private Long unixTime;
    private String url;

    EarthquakeClass(String l, Double m, Long t, String u){
        loc = l;
        mag = m;
        unixTime = t;
        url = u;
    }

    public Double getMagnitude(){
        return mag;
    }

    public String getLoc(){
        return(loc);
    }

    public String getUrl(){
        return(url);
    }


    public String getDate(){
        Date unixDate = new Date(unixTime);
        SimpleDateFormat fmtStr = new SimpleDateFormat("MMM dd, yyyy");
        String date = fmtStr.format(unixDate);

        return date;
    }

    public String getTime(){
        Date milliTime = new Date(unixTime);
        SimpleDateFormat fmtStr = new SimpleDateFormat("hh:mm aa");
        String time = fmtStr.format(milliTime);

        return time;
    }

    @Override
    public String toString(){
        return(String.format(Locale.US, "%-3.1f %s %s",mag, loc, getDate()));
    }
}
