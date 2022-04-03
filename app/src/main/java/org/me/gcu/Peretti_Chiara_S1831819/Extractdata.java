package org.me.gcu.Peretti_Chiara_S1831819;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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

    public String removeLineBreak(String text){

        text = text.replace("<br />"," ");

        return text;
    }
}
