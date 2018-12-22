package com.example.bleh.myapplication.DB;

import android.app.Application;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import java.util.Arrays;
import java.util.Arrays.*;
import java.util.List;

@Entity
public class Food {
    @PrimaryKey(autoGenerate = true)
    public int foodID;

    @ColumnInfo(name = "food_name")
    public String food_name;

    @ColumnInfo(name = "calories")
    public float calorie_value;


    public float getCalorieValue() {
        return calorie_value;
    }

    public void setCalorieValue(float calorie_value) {
        this.calorie_value = calorie_value;
    }

    public String getFoodName() {
        return food_name;
    }

    public void setFoodName(String food_name) {
        this.food_name = food_name;
    }


    public Food(String foodName, float calories)
    {
        calorie_value=calories;
        food_name = foodName;
    }
    public Food(){}

    public static Food[] populateFood() {
        return new Food[] {
                new Food("Scrambled Eggs", 199),
                new Food("Hard Boiled Eggs", 79),
                new Food("Mankoushe Zaatar", 280),
                new Food("Mankoushe Cheese", 750),
                new Food("Mankoushe Keshek", 290),
                new Food("Croissant", 100),
                new Food("Foul Mudammas", 110),
                new Food("Halloumi Cheese", 96),
                new Food("Toast", 100),
                new Food("Labne", 70),
                new Food("Milk and Cereal", 270),
                new Food("Oatmeal", 68),
                new Food("Keshek", 133),
                new Food("Pizza (light serving)", 532),
                new Food("Pizza (heavy serving)", 1064),
                new Food("Pasta (red sauce)", 69),
                new Food("Pasta (white sauce)", 254),
                new Food("Pasta (pesto sauce)", 414),
                new Food("Lasagna", 310),
                new Food("Fattoush", 113),
                new Food("Tabbouleh", 124),
                new Food("Caesar Salad", 200),
                new Food("Mloukhiye", 290),
                new Food("Fasolya w Riz", 324),
                new Food("Bazela w Riz", 155),
                new Food("Beef Shawarma Sandwich", 198),
                new Food("Chicken Shawarma Sandwich", 430),
                new Food("Hotdog", 111),
                new Food("Fried Chicken Leg", 254),
                new Food("French Fries", 312),
                new Food("Steak (100 g)", 271),
                new Food("Grilled Chicken (100 g)", 81),
                new Food("Fish Fillet (100 g)", 232),
                new Food("Breaded Shrimp", 277),
                new Food("Breaded Chicken", 320)

        };
    }
}
