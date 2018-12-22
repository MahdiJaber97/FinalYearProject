package com.example.bleh.myapplication.DB;

import android.arch.persistence.db.SupportSQLiteStatement;
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
public class ExerciseDao_Impl implements ExerciseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfExercise;

  public ExerciseDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfExercise = new EntityInsertionAdapter<Exercise>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Exercise`(`exerciseId`,`exercise_name`,`met`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Exercise value) {
        stmt.bindLong(1, value.exerciseId);
        if (value.exercise_name == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.exercise_name);
        }
        stmt.bindDouble(3, value.met_value);
      }
    };
  }

  @Override
  public void insertAll(Exercise[] Exercises) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfExercise.insert(Exercises);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Exercise> getAll() {
    final String _sql = "SELECT * FROM Exercise";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfExerciseId = _cursor.getColumnIndexOrThrow("exerciseId");
      final int _cursorIndexOfExerciseName = _cursor.getColumnIndexOrThrow("exercise_name");
      final int _cursorIndexOfMetValue = _cursor.getColumnIndexOrThrow("met");
      final List<Exercise> _result = new ArrayList<Exercise>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Exercise _item;
        _item = new Exercise();
        _item.exerciseId = _cursor.getInt(_cursorIndexOfExerciseId);
        _item.exercise_name = _cursor.getString(_cursorIndexOfExerciseName);
        _item.met_value = _cursor.getDouble(_cursorIndexOfMetValue);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Exercise findByName(String exName) {
    final String _sql = "SELECT * FROM Exercise WHERE exercise_name LIKE ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (exName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, exName);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfExerciseId = _cursor.getColumnIndexOrThrow("exerciseId");
      final int _cursorIndexOfExerciseName = _cursor.getColumnIndexOrThrow("exercise_name");
      final int _cursorIndexOfMetValue = _cursor.getColumnIndexOrThrow("met");
      final Exercise _result;
      if(_cursor.moveToFirst()) {
        _result = new Exercise();
        _result.exerciseId = _cursor.getInt(_cursorIndexOfExerciseId);
        _result.exercise_name = _cursor.getString(_cursorIndexOfExerciseName);
        _result.met_value = _cursor.getDouble(_cursorIndexOfMetValue);
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
