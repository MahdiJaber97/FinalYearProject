package com.example.bleh.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class DetailedMeasurements extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_measurements);
        ArrayList<Item> itemList = new ArrayList<Item>();
        itemList.add(new Item("Item " + 1, Item.ItemType.ONE_ITEM));
        itemList.add(new Item("Item " + 2, Item.ItemType.TWO_ITEM));
        itemList.add(new Item("Item " + 3, Item.ItemType.THREE_ITEM));
        itemList.add(new Item("Item " + 4, Item.ItemType.FOUR_ITEM));
        itemList.add(new Item("Item " + 5, Item.ItemType.FIVE_ITEM));
        recyclerView = findViewById(R.id.recyclerview);
        ItemArrayAdapter itemArrayAdapter = new ItemArrayAdapter(itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemArrayAdapter);
    }
}
