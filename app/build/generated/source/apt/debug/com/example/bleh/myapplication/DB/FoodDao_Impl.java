package com.example.bleh.myapplication.DB;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class FoodDao_Impl implements FoodDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfFood;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfFood;

  public FoodDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFood = new EntityInsertionAdapter<Food>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Food`(`foodID`,`food_name`,`calories`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Food value) {
        stmt.bindLong(1, value.foodID);
        if (value.food_name == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.food_name);
        }
        stmt.bindDouble(3, value.calorie_value);
      }
    };
    this.__deletionAdapterOfFood = new EntityDeletionOrUpdateAdapter<Food>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Food` WHERE `foodID` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Food value) {
        stmt.bindLong(1, value.foodID);
      }
    };
  }

  @Override
  public void insertAll(Food[] food) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfFood.insert(food);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insert(Food food) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfFood.insert(food);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteFood(Food... food) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfFood.handleMultiple(food);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Food> getAll() {
    final String _sql = "SELECT * FROM food";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfFoodID = _cursor.getColumnIndexOrThrow("foodID");
      final int _cursorIndexOfFoodName = _cursor.getColumnIndexOrThrow("food_name");
      final int _cursorIndexOfCalorieValue = _cursor.getColumnIndexOrThrow("calories");
      final List<Food> _result = new ArrayList<Food>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Food _item;
        _item = new Food();
        _item.foodID = _cursor.getInt(_cursorIndexOfFoodID);
        _item.food_name = _cursor.getString(_cursorIndexOfFoodName);
        _item.calorie_value = _cursor.getFloat(_cursorIndexOfCalorieValue);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Food findByName(String foodName) {
    final String _sql = "SELECT * FROM food WHERE food_name LIKE ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (foodName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, foodName);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfFoodID = _cursor.getColumnIndexOrThrow("foodID");
      final int _cursorIndexOfFoodName = _cursor.getColumnIndexOrThrow("food_name");
      final int _cursorIndexOfCalorieValue = _cursor.getColumnIndexOrThrow("calories");
      final Food _result;
      if(_cursor.moveToFirst()) {
        _result = new Food();
        _result.foodID = _cursor.getInt(_cursorIndexOfFoodID);
        _result.food_name = _cursor.getString(_cursorIndexOfFoodName);
        _result.calorie_value = _cursor.getFloat(_cursorIndexOfCalorieValue);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
