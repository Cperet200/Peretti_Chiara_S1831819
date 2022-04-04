package org.me.gcu.Peretti_Chiara_S1831819;

import static java.lang.Float.parseFloat;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.maps.model.LatLng;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Extractdata {

    public Extractdata() {
    }


    //Extract road from title
    public String getRoadFromTitle(String text){
        int index = text.indexOf(' ');

        if (index > -1) { // Check if there is more than one word.

            return text.substring(0, index).trim(); // Extract first word.

        } else {

            return text; // Text is the first word itself.
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String[] getDropdownList(List<Roadworks> roadworksList) {

        List<String> list = new ArrayList<>();
        list.add("All");
        for(int i = 0; i < roadworksList.size(); i ++){
            list.add(getRoadFromTitle(roadworksList.get(i).getTitle()));
        }
        list = list.stream().distinct().collect(Collectors.toList());
        String[] result = list.toArray(new String[0]);

        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDate getStartDate(String text){

        String arr[] = text.split("br />");
        String startDateString = arr[0].split(",")[1].split("-")[0].trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(startDateString, formatter);
        return date;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDate getEndDate(String text){


        String arr[] = text.split("br />");
        String endDateString = arr[1].split(",")[1].split("-")[0].trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(endDateString, formatter);
        return date;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDate convertCalendarToDate(String date){


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
        LocalDate calendarDate = LocalDate.parse(date, formatter);
        return calendarDate;
    }

    public LatLng getLatLng(String text){
        String[] latLong = text.split(" ");
        double lat = parseFloat(latLong[0]);
        double lon = parseFloat(latLong[1]);
        LatLng location = new LatLng(lat, lon);
        return location;
    }


    public String removeLineBreak(String text){

        text = text.replace("<br />"," ");

        return text;
    }
}
