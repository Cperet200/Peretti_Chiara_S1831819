package org.me.gcu.Peretti_Chiara_S1831819;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickListener
{
    private TextView rawDataDisplay;
    private Button startButton;
    private String result = "";
    private String url1="";
    List<Roadworks> roadworksList;

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
        rawDataDisplay = (TextView)findViewById(R.id.rawDataDisplay);
        startButton = (Button)findViewById(R.id.startButton);
        recyclerView = findViewById(R.id.recyclerView);
        startButton.setOnClickListener(this);
        Log.e("MyTag","after startButton");
        // More Code goes here
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



    @Override
    public void onClick(View v)
    {
        Log.e("MyTag","in onClick");
        startProgress();
        Log.e("MyTag","after startProgress");
    }

    private LinkedList<Roadworks> parse(String datatoParse)
    {
        Roadworks roadworks = null;
        LinkedList<Roadworks> roadworksList = null;


        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(new StringReader(datatoParse));

            int eventType = parser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT) {


                if(eventType == XmlPullParser.START_TAG) {

                    if (parser.getName().equalsIgnoreCase("channel")) {

                        roadworksList = new LinkedList<Roadworks>();
                        System.out.println(parser.getName());
                    }
                    else if (parser.getName().equalsIgnoreCase("item")) {
                        // add Roadwork object to list
                        roadworks = new Roadworks();
                        System.out.println(parser.getName());


                    } else if (roadworks != null && parser.getName().equalsIgnoreCase("title")){
                        System.out.println(parser.getName());

                        String text = parser.nextText();
                        System.out.println(text);
                        roadworks.setTitle(text);
                    } else if (roadworks != null && parser.getName().equalsIgnoreCase("description")) {
                        System.out.println(parser.getName());

                        String text = parser.nextText();
                        System.out.println(text);
                        roadworks.setDescription(text);
                    } else if (roadworks != null && parser.getName().equalsIgnoreCase("link")) {

                        System.out.println(parser.getName());

                    }else if (parser.getName().equalsIgnoreCase("georss:point")) {
                        System.out.println(parser.getName());

                        String text = parser.nextText();
                        System.out.println(text);
                        roadworks.setGeorss(text);
                    }




                }

                else if (eventType == XmlPullParser.END_TAG)
                {
                    if (parser.getName().equalsIgnoreCase("item")){
                        Log.e("MyTag","item is" + roadworks.toString());
                        roadworksList.add(roadworks);
                    }
                    else if(parser.getName().equalsIgnoreCase("channel")){
                        int size;
                        size = roadworksList.size();
                        Log.e("My tag", "size of channel is " + size);
                    }
                }

                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}

        return roadworksList;
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
                    Log.e("MyTag",inputLine);

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
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    XMLPullParserHandler parser = new XMLPullParserHandler();

                    roadworksList = parse(result);
//                    rawDataDisplay.setText(roadworksList.toString());
//                    setAdapter(roadworksList);


                }
            });
        }

    }


}