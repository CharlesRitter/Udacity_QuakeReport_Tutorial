/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/*
TODO Give the user options to decide what type of quakes they want to display.
TODO Override the onChanged() function so that the screen will update if the data changes
 */

public class EarthquakeActivity extends AppCompatActivity{
    private final String USGS_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        EarthquakeModel model = ViewModelProviders.of(this).get(EarthquakeModel.class);

        //copied this line here from the Android dev site.
        //ATM I have no idea what lambda functions do
        model.getEarthquakes(USGS_URL).observe(this, users ->{
            // Create a list of earthquake locations.
            List<EarthquakeClass> earthquakes = model.getEarthquakes(USGS_URL).getValue();

            // Find a reference to the {@link ListView} in the layout
            final ListView earthquakeListView = findViewById(R.id.list);

            // Create a new {@link ArrayAdapter} of earthquakes
            EqAdapter adapter = new EqAdapter(this, earthquakes);

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            earthquakeListView.setAdapter(adapter);

            //Set the click listener for the list items
            //TODO figure out how lambda functions work
            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    EarthquakeClass llItem = (EarthquakeClass) adapterView.getItemAtPosition(i);
                    String eqUrl = llItem.getUrl();
                    Intent openUrl = new Intent(Intent.ACTION_VIEW);
                    openUrl.setData(Uri.parse(eqUrl));
                    startActivity(openUrl);
                }
            });
        });

    }

}