package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EqAdapter extends ArrayAdapter<EarthquakeClass> {

    EqAdapter(Activity context, List<EarthquakeClass> eqList){
        super(context, 0, eqList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.custom_list, parent, false);
        }

        EarthquakeClass currentItem = getItem(position);

        //Print the magnitude of the quake
        TextView magnitudeView = listItemView.findViewById(R.id.magnitude);
        DecimalFormat dblStr = new DecimalFormat("0.0");
        magnitudeView.setText(dblStr.format(currentItem.getMagnitude()));
        //set the background color for the magnitude
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
        int magnitudeColor = getMagnitudeColor(currentItem.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);

        //Print the location of the quake
        TextView offsetView= listItemView.findViewById(R.id.offset);
        TextView locView = listItemView.findViewById(R.id.location);

        String unformattedLoc = currentItem.getLoc();
        //there are two different formats for the "name" string and so there are two
        //possibilities for how it will be printed
        if(unformattedLoc.contains("of")){
            int split = unformattedLoc.indexOf("of") + 2;
            String offset = unformattedLoc.substring(0, split);
            String loc = unformattedLoc.substring(split + 1);

            offsetView.setText(offset);
            locView.setText(loc);
        } else {
            offsetView.setText(R.string.no_offset);
            locView.setText(unformattedLoc);
        }

        //print the date of the quake
        TextView dateView = listItemView.findViewById(R.id.date);
        dateView.setText(currentItem.getDate());

        //print the time of the quake
        TextView timeView = listItemView.findViewById(R.id.time);
        timeView.setText(currentItem.getTime());

        return(listItemView);
    }

    private int getMagnitudeColor(Double magnitude){
        switch (magnitude.intValue())
        {
            case 0:
            case 1:
                return(ContextCompat.getColor(getContext(), R.color.magnitude1));
            case 2:
                return(ContextCompat.getColor(getContext(), R.color.magnitude2));
            case 3:
                return(ContextCompat.getColor(getContext(), R.color.magnitude3));
            case 4:
                return(ContextCompat.getColor(getContext(), R.color.magnitude4));
            case 5:
                return(ContextCompat.getColor(getContext(), R.color.magnitude5));
            case 6:
                return(ContextCompat.getColor(getContext(), R.color.magnitude6));
            case 7:
                return(ContextCompat.getColor(getContext(), R.color.magnitude7));
            case 8:
                return(ContextCompat.getColor(getContext(), R.color.magnitude8));
            case 9:
                return(ContextCompat.getColor(getContext(), R.color.magnitude9));
            default:
                return(ContextCompat.getColor(getContext(), R.color.magnitude10plus));
        }
    }
}
