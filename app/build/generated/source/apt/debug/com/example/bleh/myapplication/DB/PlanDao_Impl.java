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

@SuppressWarnings("unchecked")
public class PlanDao_Impl implements PlanDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfPlan;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfPlan;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfPlan;

  public PlanDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPlan = new EntityInsertionAdapter<Plan>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Plan`(`planid`,`uid`,`type`,`nbOfDays`,`amount`,`progress`,`bmr`,`workoutPerWeek`,`currentWeight`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Plan value) {
        stmt.bindLong(1, value.planid);
        stmt.bindLong(2, value.uid);
        if (value.type == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.type);
        }
        stmt.bindLong(4, value.nbOfDays);
        stmt.bindDouble(5, value.amount);
        stmt.bindLong(6, value.progress);
        stmt.bindDouble(7, value.bmr);
        stmt.bindLong(8, value.workoutPerWeek);
        stmt.bindDouble(9, value.currentWeight);
      }
    };
    this.__deletionAdapterOfPlan = new EntityDeletionOrUpdateAdapter<Plan>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Plan` WHERE `planid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Plan value) {
        stmt.bindLong(1, value.planid);
      }
    };
    this.__updateAdapterOfPlan = new EntityDeletionOrUpdateAdapter<Plan>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Plan` SET `planid` = ?,`uid` = ?,`type` = ?,`nbOfDays` = ?,`amount` = ?,`progress` = ?,`bmr` = ?,`workoutPerWeek` = ?,`currentWeight` = ? WHERE `planid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Plan value) {
        stmt.bindLong(1, value.planid);
        stmt.bindLong(2, value.uid);
        if (value.type == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.type);
        }
        stmt.bindLong(4, value.nbOfDays);
        stmt.bindDouble(5, value.amount);
        stmt.bindLong(6, value.progress);
        stmt.bindDouble(7, value.bmr);
        stmt.bindLong(8, value.workoutPerWeek);
        stmt.bindDouble(9, value.currentWeight);
        stmt.bindLong(10, value.planid);
      }
    };
  }

  @Override
  public long insert(Plan plan) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfPlan.insertAndReturnId(plan);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(Plan plan) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfPlan.handle(plan);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(Plan plan) {
    __db.beginTransaction();
    try {
      __updateAdapterOfPlan.handle(plan);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Plan getPlanById(int planId) {
    final String _sql = "SELECT * FROM [plan] WHERE planid = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, planId);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfPlanid = _cursor.getColumnIndexOrThrow("planid");
      final int _cursorIndexOfUid = _cursor.getColumnIndexOrThrow("uid");
      final int _cursorIndexOfType = _cursor.getColumnIndexOrThrow("type");
      final int _cursorIndexOfNbOfDays = _cursor.getColumnIndexOrThrow("nbOfDays");
      final int _cursorIndexOfAmount = _cursor.getColumnIndexOrThrow("amount");
      final int _cursorIndexOfProgress = _cursor.getColumnIndexOrThrow("progress");
      final int _cursorIndexOfBmr = _cursor.getColumnIndexOrThrow("bmr");
      final int _cursorIndexOfWorkoutPerWeek = _cursor.getColumnIndexOrThrow("workoutPerWeek");
      final int _cursorIndexOfCurrentWeight = _cursor.getColumnIndexOrThrow("currentWeight");
      final Plan _result;
      if(_cursor.moveToFirst()) {
        _result = new Plan();
        _result.planid = _cursor.getInt(_cursorIndexOfPlanid);
        _result.uid = _cursor.getInt(_cursorIndexOfUid);
        _result.type = _cursor.getString(_cursorIndexOfType);
        _result.nbOfDays = _cursor.getInt(_cursorIndexOfNbOfDays);
        _result.amount = _cursor.getDouble(_cursorIndexOfAmount);
        _result.progress = _cursor.getInt(_cursorIndexOfProgress);
        _result.bmr = _cursor.getDouble(_cursorIndexOfBmr);
        _result.workoutPerWeek = _cursor.getInt(_cursorIndexOfWorkoutPerWeek);
        _result.currentWeight = _cursor.getDouble(_cursorIndexOfCurrentWeight);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Plan findByName(int uid, int planId) {
    final String _sql = "SELECT * FROM [plan] WHERE planid = ? AND uid = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, planId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, uid);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfPlanid = _cursor.getColumnIndexOrThrow("planid");
      final int _cursorIndexOfUid = _cursor.getColumnIndexOrThrow("uid");
      final int _cursorIndexOfType = _cursor.getColumnIndexOrThrow("type");
      final int _cursorIndexOfNbOfDays = _cursor.getColumnIndexOrThrow("nbOfDays");
      final int _cursorIndexOfAmount = _cursor.getColumnIndexOrThrow("amount");
      final int _cursorIndexOfProgress = _cursor.getColumnIndexOrThrow("progress");
      final int _cursorIndexOfBmr = _cursor.getColumnIndexOrThrow("bmr");
      final int _cursorIndexOfWorkoutPerWeek = _cursor.getColumnIndexOrThrow("workoutPerWeek");
      final int _cursorIndexOfCurrentWeight = _cursor.getColumnIndexOrThrow("currentWeight");
      final Plan _result;
      if(_cursor.moveToFirst()) {
        _result = new Plan();
        _result.planid = _cursor.getInt(_cursorIndexOfPlanid);
        _result.uid = _cursor.getInt(_cursorIndexOfUid);
        _result.type = _cursor.getString(_cursorIndexOfType);
        _result.nbOfDays = _cursor.getInt(_cursorIndexOfNbOfDays);
        _result.amount = _cursor.getDouble(_cursorIndexOfAmount);
        _result.progress = _cursor.getInt(_cursorIndexOfProgress);
        _result.bmr = _cursor.getDouble(_cursorIndexOfBmr);
        _result.workoutPerWeek = _cursor.getInt(_cursorIndexOfWorkoutPerWeek);
        _result.currentWeight = _cursor.getDouble(_cursorIndexOfCurrentWeight);
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
