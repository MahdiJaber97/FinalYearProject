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

    @ColumnInfo(name = "food_type")
    public String food_type;

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

    public String getFoodType() {
        return food_type;
    }

    public void setFoodType(String food_type) {
        this.food_type = food_type;
    }


    public Food(String foodName,String foodType, float calories)
    {
        calorie_value=calories;
        food_name = foodName;
        food_type = foodType;
    }
    public Food(){}

    public static Food[] populateFood() {
        return new Food[] {
                new Food("Scrambled Eggs","Breakfast and Dinner", 199),
                new Food("Hard Boiled Eggs","Breakfast and Dinner", 79),
                new Food("Mankoushe Zaatar","Breakfast", 280),
                new Food("Mankoushe Cheese","Breakfast", 750),
                new Food("Mankoushe Keshek","Breakfast", 290),
                new Food("Croissant","Breakfast", 100),
                new Food("Foul Mudammas","Breakfast and Dinner", 110),
                new Food("Halloumi Cheese","Breakfast and Dinner", 96),
                new Food("Toast","Breakfast and Dinner", 100),
                new Food("Labne","Breakfast and Dinner", 70),
                new Food("Milk and Cereal","Breakfast", 270),
                new Food("Oatmeal","Breakfast", 68),
                new Food("Keshek","Dinner", 133),
                new Food("Pizza (light serving)","Lunch", 532),
                new Food("Pizza (heavy serving)","Lunch", 1064),
                new Food("Pasta (red sauce)","Lunch", 69),
                new Food("Pasta (white sauce)","Lunch", 254),
                new Food("Pasta (pesto sauce)","Lunch", 414),
                new Food("Lasagna","Lunch", 310),
                new Food("Fattoush","Lunch", 113),
                new Food("Tabbouleh","Lunch", 124),
                new Food("Caesar Salad","Lunch", 200),
                new Food("Mloukhiye","Lunch", 290),
                new Food("Fasolya w Riz","Lunch", 324),
                new Food("Bazela w Riz","Lunch", 155),
                new Food("Beef Shawarma Sandwich","Lunch", 198),
                new Food("Chicken Shawarma Sandwich","Lunch", 430),
                new Food("Hotdog","Lunch", 111),
                new Food("Fried Chicken Leg","Lunch", 254),
                new Food("French Fries","Lunch", 312),
                new Food("Steak (100 g)","Lunch", 271),
                new Food("Grilled Chicken (100 g)","Lunch", 81),
                new Food("Fish Fillet (100 g)","Lunch", 232),
                new Food("Breaded Shrimp","Lunch", 277),
                new Food("Breaded Chicken","Lunch", 320)

        };
    }
}
