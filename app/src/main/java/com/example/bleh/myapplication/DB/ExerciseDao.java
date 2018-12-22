package com.example.bleh.myapplication.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Query("SELECT * FROM Exercise")
    List<Exercise> getAll();

    @Query("SELECT * FROM Exercise WHERE exercise_name LIKE :exName LIMIT 1")
    Exercise findByName(String exName);

    @Insert
    void insertAll(Exercise[] Exercises);
}

