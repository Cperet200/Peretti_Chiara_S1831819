package org.me.gcu.Peretti_Chiara_S1831819;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;

public class XMLPullParserHandler {

    private LinkedList<Roadworks> roadworksList = null;
    private Roadworks roadworks;
    private String text;




    public LinkedList<Roadworks> parse(String datatoParse) {

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(new StringReader((datatoParse)));

            int eventType = parser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();

                if(eventType == XmlPullParser.START_TAG) {

                        if (tagname.equalsIgnoreCase("channel")) {

                            roadworksList = new LinkedList<Roadworks>();
                        }
                        else if (tagname.equalsIgnoreCase("item")) {
                            // add Roadwork object to list
                            roadworks = new Roadworks();
                        }else if (tagname.equalsIgnoreCase("title")) {
                            String text = parser.nextText();
                            roadworks.setTitle(text);
                        }  else if (tagname.equalsIgnoreCase("description")) {
                            String text = parser.nextText();
                            roadworks.setDescription(text);
                        } else if (tagname.equalsIgnoreCase("georss:point")) {
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


