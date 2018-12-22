package com.example.bleh.myapplication.DB;

import android.arch.persistence.room.*;


import java.util.List;

@Dao
public interface FoodDao {
    @Query("SELECT * FROM food")
    List<Food> getAll();

    @Query("SELECT * FROM food WHERE food_name LIKE :foodName LIMIT 1")
    Food findByName(String foodName);

    @Insert
    void insertAll(Food[] food);

    @Insert
    void insert(Food food);

    @Delete
    void deleteFood(Food... food);

}