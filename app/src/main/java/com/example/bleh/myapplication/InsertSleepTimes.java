package com.example.bleh.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class InsertSleepTimes extends AppCompatActivity {
    Spinner spinnerS1, spinnerS2, spinnerS3;
    Spinner spinnerW1, spinnerW2, spinnerW3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_sleep_times);
        spinnerS1 = findViewById(R.id.spinnerS1);
        spinnerS2 = findViewById(R.id.spinnerS2);
        spinnerS3 = findViewById(R.id.spinnerS3);
        spinnerW1 = findViewById(R.id.spinnerW1);
        spinnerW2 = findViewById(R.id.spinnerW2);
        spinnerW3 = findViewById(R.id.spinnerW3);
        final List<String> spinnerType =  new ArrayList<String>();
        final List<String> spinnerType1 =  new ArrayList<String>();
        final List<String> spinnerType2 =  new ArrayList<String>();
        for(int i =1;i<=12;i++)
        {
            spinnerType.add(""+i);
        }
        for(int i =0;i<60;i++)
        {
            spinnerType1.add(""+i);
        }
        spinnerType2.add("PM");
        spinnerType2.add("AM");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerType);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerType1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerType2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerS1.setAdapter(adapter);
        spinnerW1.setAdapter(adapter);
        spinnerS2.setAdapter(adapter1);
        spinnerW2.setAdapter(adapter1);
        spinnerS3.setAdapter(adapter2);
        spinnerW3.setAdapter(adapter2);
    }
}
