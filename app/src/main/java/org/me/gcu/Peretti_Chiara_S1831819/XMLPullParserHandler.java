package org.me.gcu.Peretti_Chiara_S1831819;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.LinkedList;

public class XMLPullParserHandler {

    private LinkedList<Roadworks> roadworksList = null;
    private Roadworks roadworks;
    private String text;
    Extractdata extractdata = new Extractdata();




    @RequiresApi(api = Build.VERSION_CODES.O)
    public LinkedList<Roadworks> parse(String datatoParse) {

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

                    }
                    else if (parser.getName().equalsIgnoreCase("item")) {
                        // add Roadwork object to list
                        roadworks = new Roadworks();



                    } else if (roadworks != null && parser.getName().equalsIgnoreCase("title")){
                        String text = parser.nextText();
                        roadworks.setTitle(text);
                    }
                    else if (roadworks != null && parser.getName().equalsIgnoreCase("description")) {
                        String text = parser.nextText();
                        roadworks.setDescription(text);

                        LocalDate sDate = extractdata.getStartDate(text);
                        LocalDate eDate = extractdata.getEndDate(text);
                        roadworks.setStartDate(sDate);
                        roadworks.setEndDate(eDate);
                    } else if (parser.getName().equalsIgnoreCase("point")) {
                        String text = parser.nextText();
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
}


