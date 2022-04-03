package org.me.gcu.Peretti_Chiara_S1831819;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements OnClickListener
{
    private Button startButton;
    private String result = "";
    private CalendarView calendarView;
    private TextView textView;
    List<Roadworks> roadworksList;
    List<Roadworks> filteredRoadworksList;
    Extractdata extractdata = new Extractdata();

    private RecyclerView recyclerView;
        // Traffic Scotland Planned Roadworks XML link
    private String urlSource="https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MyTag","in onCreate");
        // Set up the raw links to the graphical components
        startButton = (Button)findViewById(R.id.startButton);
        textView = (TextView)findViewById(R.id.salutation);
        recyclerView = findViewById(R.id.recyclerView);
        startButton.setOnClickListener(this);
        calendarView = findViewById(R.id.calendarView);
        calendarView.setVisibility(View.INVISIBLE);

        Log.e("MyTag","after startButton");

    }

    private void setDateFilter() {
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                i1 ++;
                String date = i2 + "-" + i1 + "-" + i;
                LocalDate targetDate;
                System.out.println(date);
                targetDate = extractdata.convertCalendarToDate(date);

                List<Roadworks> firstFilteredRoadworksList = roadworksList.stream().filter(d -> d.getStartDate().isBefore(targetDate) || d.getStartDate().isEqual(targetDate)).collect(Collectors.toList());
                filteredRoadworksList = firstFilteredRoadworksList.stream().filter(d -> d.getEndDate().isAfter(targetDate) || d.getEndDate().isEqual(targetDate)).collect(Collectors.toList());
                System.out.println(firstFilteredRoadworksList.size());
                System.out.println(filteredRoadworksList.size());

                if (filteredRoadworksList.size() > 0){
                    filterView(filteredRoadworksList);
                    System.out.println("filtering data");
                    textView.setText(filteredRoadworksList.size() + " roadworks are happening on this date.");
                } else {
                    Roadworks roadworks = new Roadworks();
                    roadworks.setTitle("No roadworks scheduled for this date.");
                    textView.setText("No roadworks are happening on this date.");
                    filteredRoadworksList.add(roadworks);
                    System.out.println("No roadworks for these dates");
                    filterView(filteredRoadworksList);

                }

            }
        });
    }

    public void startProgress()
    {
        // Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();

    } //

    private void setAdapter(List<Roadworks> roadworksList){
        recyclerAdapter adapter = new recyclerAdapter(this, roadworksList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


    }

    private void filterView(List<Roadworks> data){
        recyclerAdapter adapter = new recyclerAdapter(this, data);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }




    @Override
    public void onClick(View v)
    {
        Log.e("MyTag","in onClick");
        startProgress();
        Log.e("MyTag","after startProgress");
    }



    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable
    {
        private String url;

        public Task(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run()
        {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";


            Log.e("MyTag","in run");

            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                Log.e("MyTag","after ready");
                //
                // Now read the data. Make sure that there are no specific hedrs
                // in the data file that you need to ignore.
                // The useful data that you need is in each of the item entries
                //
                while ((inputLine = in.readLine()) != null)
                {
                    result = result + inputLine;
                    //Log.e("MyTag",inputLine);

                }
                in.close();




            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception in run");
            }

            //
            // Now that you have the xml data you can parse it
            //











            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !



            MainActivity.this.runOnUiThread(new Runnable()
            {
                @RequiresApi(api = Build.VERSION_CODES.O)
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    XMLPullParserHandler parser = new XMLPullParserHandler();

                    roadworksList = parser.parse(result);
                    setAdapter(roadworksList);
                    calendarView.setVisibility(View.VISIBLE);
                    setDateFilter();


                }
            });
        }

    }


}