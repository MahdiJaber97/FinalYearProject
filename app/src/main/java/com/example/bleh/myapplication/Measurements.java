package com.example.bleh.myapplication;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class Measurements extends AppCompatActivity
{
    public static final String TAG = "Measurements";
    private LineChart mChart;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurements);
        mChart = findViewById(R.id.LineChart);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        ArrayList<Entry> yValues = new ArrayList<>();
        yValues.add(new Entry(0,60f));
        yValues.add(new Entry(1,60f));
        yValues.add(new Entry(2,50f));
        yValues.add(new Entry(3,40f));
        yValues.add(new Entry(4,80f));
        yValues.add(new Entry(5,10f));
        yValues.add(new Entry(6,50f));
        yValues.add(new Entry(7,30f));
        LineDataSet set1 = new LineDataSet(yValues,"Data Set 1");
        set1.setFillAlpha(110);
        set1.setColor(Color.GREEN);
        set1.setLineWidth(3f);
        set1.setValueTextSize(10f);
        set1.setValueTextColor(Color.BLUE);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        mChart.setData(data);
    }
}
