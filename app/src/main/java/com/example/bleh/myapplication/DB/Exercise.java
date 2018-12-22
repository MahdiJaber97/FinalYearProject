package com.example.bleh.myapplication.DB;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    public int exerciseId;

    @ColumnInfo(name = "exercise_name")
    public String exercise_name;

    @ColumnInfo(name = "met")
    public double met_value;

    public double getMetValue() {
        return met_value;
    }

    public void setMetValue(double met_value) {
        this.met_value = met_value;
    }

    public String getExerciseName() {
        return exercise_name;
    }

    public void setExerciseName(String exercise_name) {
        this.exercise_name = exercise_name;
    }

    public Exercise(String exerciseName, double met)
    {
        met_value=met;
        exercise_name= exerciseName;
    }

    public Exercise()
    {
    }

    public static Exercise[] populateExercises() {
        return new Exercise[] {
                new Exercise("Bicycling", 7.5),
                new Exercise("Yoga", 2.8),
                new Exercise("Push ups/Sit ups/Pull ups", 3.8),
                new Exercise("Circuit Training", 8.0),
                new Exercise("Squatting", 5.0),
                new Exercise("Home Exercising", 3.8),
                new Exercise("Traidmill", 9.0),
                new Exercise("Rope Skipping", 12.3),
                new Exercise("Rowing", 6.0),
                new Exercise("Pilates", 3.0),
                new Exercise("Yoga", 2.3),
                new Exercise("General Dancing", 7.8),
                new Exercise("Aerobic", 6.0),
                new Exercise("Fishing", 3.5),
                new Exercise("General Hunting", 5.0),
                new Exercise("Rifle Exercises", 2.5),
                new Exercise("Home Cleaning", 3.5),
                new Exercise("Inactivity", 1.3),
                new Exercise("Jogging", 7.0),
                new Exercise("Running", 9.0),
                new Exercise("Running Marathon", 13.3),
                new Exercise("Archery", 4.3),
                new Exercise("Badminton", 7.0),
                new Exercise("Basketball", 6.5),
                new Exercise("Billiards", 2.5),
                new Exercise("Bowling", 3.8),
                new Exercise("Boxing", 12.8),
                new Exercise("Football", 8.0),
                new Exercise("Golf", 4.8),
                new Exercise("Gymnastics", 3.8),
                new Exercise("Horseback Riding", 5.5),
                new Exercise("Martial Arts", 10.3),
                new Exercise("Rock/Mountain Climbing", 8.0),
                new Exercise("Soccer", 7.0),
                new Exercise("Rollerblading", 9.8),
                new Exercise("Skateboarding", 5.0),
                new Exercise("Ping Pong", 4.0),
                new Exercise("Tennis", 7.3),
                new Exercise("Volleyball", 3.0),
                new Exercise("Stair Climbing", 5.5),
                new Exercise("Walking", 2.8),
                new Exercise("Swimming Freestyle", 5.8),
                new Exercise("Swimming Breaststroke", 10.3),
                new Exercise("Swimming Butterfly", 13.8),
                new Exercise("Ice Skating", 7.0),
                new Exercise("Surfing", 3.0),
                new Exercise("Canoe Rowing", 5.8),
                new Exercise("Skiing", 7.0)
        };
    }
}

