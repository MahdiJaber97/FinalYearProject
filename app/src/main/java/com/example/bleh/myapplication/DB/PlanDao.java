package com.example.bleh.myapplication.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface PlanDao {

    @Query("SELECT * FROM [plan] WHERE planid = :planId")
    Plan getPlanById(int planId);

    @Query("SELECT * FROM [plan] WHERE planid = :planId AND " +
            "uid = :uid LIMIT 1")
    Plan findByName(int uid, int planId);

    @Insert
    long insert(Plan plan);

    @Update
    void update(Plan plan);

    @Delete
    void delete(Plan plan);
}
