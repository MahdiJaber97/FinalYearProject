package com.example.bleh.myapplication;
import android.content.Intent;
import android.icu.util.Measure;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemArrayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;

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
        } else {
            return -1;
        }
    }


    // specify the row layout file and click for each row
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ONE)
        {
            Log.wtf("Type1","");
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_type1, parent, false);
            return new ViewHolderOne(view);
        }
        else if (viewType == TYPE_TWO)
        {
            Log.wtf("Type2","");
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_type2, parent, false);
            return new ViewHolderTwo(view);
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
}