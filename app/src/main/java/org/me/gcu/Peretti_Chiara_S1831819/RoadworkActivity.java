package org.me.gcu.Peretti_Chiara_S1831819;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;

public class RoadworkActivity extends AppCompatActivity {

    TextView title, description, point, startDateTV, endDateTV;
    String data1, data2, data3;
    LocalDate startDate, endDate;
    Extractdata extractdata = new Extractdata();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roadwork);


        title = findViewById(R.id.roadworktitle);
        description = findViewById(R.id.roadworkdescription);
        point = findViewById(R.id.roadworkpoint);
        startDateTV = findViewById(R.id.startDate);
        endDateTV = findViewById(R.id.endDate);



        getData();
        setData();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getData(){
        if(getIntent().hasExtra("title") && getIntent().hasExtra("description") && getIntent().hasExtra("point")){
            data1 = getIntent().getStringExtra("title");

            data2 = getIntent().getStringExtra("description");
            data3 = getIntent().getStringExtra("point");
            startDate = extractdata.getStartDate(data2);
            endDate = extractdata.getEndDate(data2);
            System.out.println(startDate);
            System.out.println(endDate);
            data1 = extractdata.getRoadFromTitle(data1);
            data2 = extractdata.removeLineBreak(data2);

        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData(){
        title.setText("Road: " + data1);
        description.setText("Description: " + data2);
        point.setText("Coordinates: " + data3);
        startDateTV.setText("Start date: " + startDate);
        endDateTV.setText("End date: " + endDate);
    }

}