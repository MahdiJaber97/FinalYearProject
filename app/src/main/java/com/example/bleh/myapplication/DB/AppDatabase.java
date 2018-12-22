package com.example.bleh.myapplication.DB;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.*;
import android.content.Context;
import android.support.annotation.NonNull;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Food.class,Exercise.class, Plan.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract FoodDao foodDao();
    public abstract ExerciseDao exerciseDao();
    public abstract PlanDao planDao();

    private static AppDatabase INSTANCE;

    public synchronized static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    private static AppDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class,
                "my-database")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                prePopulateData(context);

                            }
                        });
                    }
                })
                .allowMainThreadQueries()
                .build();
    }

    public UserDao getUserDao(Context context)
    {
        return getInstance(context).userDao();
    }

    public ExerciseDao getExerciseDao (Context context) { return getInstance(context).exerciseDao(); }

    public FoodDao getFoodDao (Context context)
    {
        return getInstance(context).foodDao();
    }

    public PlanDao getPlanDao (Context context)
    {
        return getInstance(context).planDao();
    }


    public static void destroymyDb() {
        INSTANCE = null;
    }

    private static void prePopulateData(Context context)
    {
        Food[] food = Food.populateFood();
        Exercise[] exercise = Exercise.populateExercises();
        INSTANCE.getFoodDao(context).insertAll(food);
        INSTANCE.getExerciseDao(context).insertAll(exercise);
    }
}