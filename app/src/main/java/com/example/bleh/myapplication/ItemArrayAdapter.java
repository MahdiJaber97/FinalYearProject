package com.example.bleh.myapplication;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.util.Measure;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ItemArrayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    private static final int TYPE_THREE = 3;
    private static final int TYPE_FOUR = 4;
    private static final int TYPE_FIVE = 5;
    private SensorManager sensorManager;
    private ArrayList<Item> itemList;
    // Constructor of the class
    public ItemArrayAdapter(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    // determine which layout to use for the row
    @Override
    public int getItemViewType(int position) {
        Item item = itemList.get(position);
        if (item.getType() == Item.ItemType.ONE_ITEM) {
            return TYPE_ONE;
        } else if (item.getType() == Item.ItemType.TWO_ITEM) {
            return TYPE_TWO;
        } else if (item.getType() == Item.ItemType.THREE_ITEM){
            return TYPE_THREE;
        } else if (item.getType() == Item.ItemType.FOUR_ITEM){
            return TYPE_FOUR;
        } else if (item.getType() == Item.ItemType.FIVE_ITEM){
            return TYPE_FIVE;
        }
        else return -1;
    }


    // specify the row layout file and click for each row
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ONE)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_type1, parent, false);
            return new ViewHolderOne(view);
        }
        else if (viewType == TYPE_TWO)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_type2, parent, false);
            return new ViewHolderTwo(view);
        }
        else if (viewType == TYPE_THREE)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_type3, parent, false);
            return new ViewHolderThree(view);
        }
        else if (viewType == TYPE_FOUR)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_type4, parent, false);
            return new ViewHolderFour(view);
        }
        else if (viewType == TYPE_FIVE)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_type5, parent, false);
            return new ViewHolderFive(view);
        }
        else {
            throw new RuntimeException("The type has to be ONE or TWO");
        }
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int listPosition) {
        switch (holder.getItemViewType()) {
            case TYPE_ONE:
                initLayoutOne((ViewHolderOne)holder, listPosition);
                break;
            case TYPE_TWO:
                initLayoutTwo((ViewHolderTwo) holder, listPosition);
                break;
            case TYPE_THREE:
                initLayoutThree((ViewHolderThree) holder, listPosition);
                break;
            case TYPE_FOUR:
                initLayoutFour((ViewHolderFour) holder, listPosition);
                break;
            case TYPE_FIVE:
                initLayoutFive((ViewHolderFive) holder, listPosition);
                break;
            default:
                break;
        }
    }

    private void initLayoutOne(final ViewHolderOne holder, int pos)
    {
        holder.Measure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Measurements.class);
                view.getContext().startActivity(intent);
            }
        });
        holder.checkOldMeasurements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Measurements.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    private void initLayoutTwo(ViewHolderTwo holder, int pos)
    {
        holder.Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),InsertSleepTimes.class);
                view.getContext().startActivity(intent);
            }
        });
        holder.checkOldMeasurements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Measurements.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    private void initLayoutThree(final ViewHolderThree holder, int pos)
    {
        holder.stepsMoved.setText("6237 Steps Moved Today!");
    }

    private void initLayoutFour(final ViewHolderFour holder, int pos)
    {

    }

    private void initLayoutFive(final ViewHolderFive holder, int pos)
    {
        holder.MeasureBodyComposition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),BodyComposition.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    // Static inner class to initialize the views of rows
    static class ViewHolderOne extends RecyclerView.ViewHolder {
        Button Measure,checkOldMeasurements;
        public ViewHolderOne(View itemView) {
            super(itemView);
            Measure = itemView.findViewById(R.id.Measure);
            checkOldMeasurements = itemView.findViewById(R.id.CheckMeasurements);
        }
    }

    static class ViewHolderTwo extends RecyclerView.ViewHolder {
        Button Insert,checkOldMeasurements;
        public ViewHolderTwo(View itemView) {
            super(itemView);
            Insert = itemView.findViewById(R.id.Insert);
            checkOldMeasurements = itemView.findViewById(R.id.CheckMeasurements);
        }
    }

    static class ViewHolderThree extends RecyclerView.ViewHolder implements SensorEventListener{
        TextView stepsMoved;
        SensorManager sensorManager;
        public ViewHolderThree(View itemView) {
            super(itemView);
            stepsMoved = itemView.findViewById(R.id.steps);
            sensorManager = (SensorManager)itemView.getContext().getSystemService(Context.SENSOR_SERVICE);
            Sensor sensorCount = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            if(sensorCount!= null)
            {
                sensorManager.registerListener(this,sensorCount,SensorManager.SENSOR_DELAY_UI);
            }
            else
            {
                Toast.makeText(itemView.getContext(),"Count Sensor Not Available",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            stepsMoved.setText(String.valueOf(sensorEvent.values[0]));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }

    static class ViewHolderFour extends RecyclerView.ViewHolder {
        Button addWater;
        TextView WaterGlasses;
        DonutProgress WaterProgress;
        public ViewHolderFour(View itemView)
        {
            super(itemView);
            addWater = itemView.findViewById(R.id.AddWater);
            WaterGlasses = itemView.findViewById(R.id.waterGlasses);
            WaterProgress = itemView.findViewById(R.id.water_progress);
            SharedPreferences sharedPreferences = itemView.getContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            final int[] waterGlasses = {sharedPreferences.getInt("WaterCount", 0)};
            WaterGlasses.setText("You have Drank "+ String.valueOf(waterGlasses[0])+" Water Glasses today");
            final DecimalFormat df = new DecimalFormat("#.##");
            double percentage = waterGlasses[0] / 7.00 * 100.00;
            percentage = Double.parseDouble(df.format(percentage));
            WaterProgress.setProgress((float)percentage);
            addWater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    waterGlasses[0]++;
                    editor.putInt("WaterCount",waterGlasses[0]);
                    editor.apply();
                    WaterGlasses.setText("You have Drank "+ String.valueOf(waterGlasses[0])+" Water Glasses today");
                    double percentage = waterGlasses[0] / 7.00 * 100.00;
                    percentage = Double.parseDouble(df.format(percentage));
                    WaterProgress.setProgress((float)percentage);
                    WaterProgress.setSuffixText("%");
                }
            });
        }
    }

    static class ViewHolderFive extends RecyclerView.ViewHolder {
        Button MeasureBodyComposition;
        public ViewHolderFive(View itemView) {
            super(itemView);
            MeasureBodyComposition = itemView.findViewById(R.id.MeasureBodyComposition);
        }
    }
}