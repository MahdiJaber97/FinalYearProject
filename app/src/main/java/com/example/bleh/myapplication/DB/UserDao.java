package com.example.bleh.myapplication.DB;

import android.arch.persistence.room.*;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE uid = :userId")
    User getUserById(int userId);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Insert
    long insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);


}
