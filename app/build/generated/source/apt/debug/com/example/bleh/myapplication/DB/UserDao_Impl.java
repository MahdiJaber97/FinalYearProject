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
public class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfUser;

  public UserDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `User`(`uid`,`first_name`,`last_name`,`username`,`password`,`birthDay`,`sex`,`height`,`weight`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.uid);
        if (value.firstName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.firstName);
        }
        if (value.lastName == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.lastName);
        }
        if (value.username == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.username);
        }
        if (value.password == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.password);
        }
        stmt.bindLong(6, value.birthDay);
        if (value.sex == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.sex);
        }
        stmt.bindDouble(8, value.height);
        stmt.bindDouble(9, value.weight);
      }
    };
    this.__deletionAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `User` WHERE `uid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.uid);
      }
    };
    this.__updateAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `User` SET `uid` = ?,`first_name` = ?,`last_name` = ?,`username` = ?,`password` = ?,`birthDay` = ?,`sex` = ?,`height` = ?,`weight` = ? WHERE `uid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.uid);
        if (value.firstName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.firstName);
        }
        if (value.lastName == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.lastName);
        }
        if (value.username == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.username);
        }
        if (value.password == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.password);
        }
        stmt.bindLong(6, value.birthDay);
        if (value.sex == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.sex);
        }
        stmt.bindDouble(8, value.height);
        stmt.bindDouble(9, value.weight);
        stmt.bindLong(10, value.uid);
      }
    };
  }

  @Override
  public long insert(User user) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfUser.insertAndReturnId(user);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(User user) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfUser.handle(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(User user) {
    __db.beginTransaction();
    try {
      __updateAdapterOfUser.handle(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public User getUserById(int userId) {
    final String _sql = "SELECT * FROM user WHERE uid = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUid = _cursor.getColumnIndexOrThrow("uid");
      final int _cursorIndexOfFirstName = _cursor.getColumnIndexOrThrow("first_name");
      final int _cursorIndexOfLastName = _cursor.getColumnIndexOrThrow("last_name");
      final int _cursorIndexOfUsername = _cursor.getColumnIndexOrThrow("username");
      final int _cursorIndexOfPassword = _cursor.getColumnIndexOrThrow("password");
      final int _cursorIndexOfBirthDay = _cursor.getColumnIndexOrThrow("birthDay");
      final int _cursorIndexOfSex = _cursor.getColumnIndexOrThrow("sex");
      final int _cursorIndexOfHeight = _cursor.getColumnIndexOrThrow("height");
      final int _cursorIndexOfWeight = _cursor.getColumnIndexOrThrow("weight");
      final User _result;
      if(_cursor.moveToFirst()) {
        _result = new User();
        _result.uid = _cursor.getInt(_cursorIndexOfUid);
        _result.firstName = _cursor.getString(_cursorIndexOfFirstName);
        _result.lastName = _cursor.getString(_cursorIndexOfLastName);
        _result.username = _cursor.getString(_cursorIndexOfUsername);
        _result.password = _cursor.getString(_cursorIndexOfPassword);
        _result.birthDay = _cursor.getInt(_cursorIndexOfBirthDay);
        _result.sex = _cursor.getString(_cursorIndexOfSex);
        _result.height = _cursor.getDouble(_cursorIndexOfHeight);
        _result.weight = _cursor.getDouble(_cursorIndexOfWeight);
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
  public User findByName(String first, String last) {
    final String _sql = "SELECT * FROM user WHERE first_name LIKE ? AND last_name LIKE ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (first == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, first);
    }
    _argIndex = 2;
    if (last == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, last);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUid = _cursor.getColumnIndexOrThrow("uid");
      final int _cursorIndexOfFirstName = _cursor.getColumnIndexOrThrow("first_name");
      final int _cursorIndexOfLastName = _cursor.getColumnIndexOrThrow("last_name");
      final int _cursorIndexOfUsername = _cursor.getColumnIndexOrThrow("username");
      final int _cursorIndexOfPassword = _cursor.getColumnIndexOrThrow("password");
      final int _cursorIndexOfBirthDay = _cursor.getColumnIndexOrThrow("birthDay");
      final int _cursorIndexOfSex = _cursor.getColumnIndexOrThrow("sex");
      final int _cursorIndexOfHeight = _cursor.getColumnIndexOrThrow("height");
      final int _cursorIndexOfWeight = _cursor.getColumnIndexOrThrow("weight");
      final User _result;
      if(_cursor.moveToFirst()) {
        _result = new User();
        _result.uid = _cursor.getInt(_cursorIndexOfUid);
        _result.firstName = _cursor.getString(_cursorIndexOfFirstName);
        _result.lastName = _cursor.getString(_cursorIndexOfLastName);
        _result.username = _cursor.getString(_cursorIndexOfUsername);
        _result.password = _cursor.getString(_cursorIndexOfPassword);
        _result.birthDay = _cursor.getInt(_cursorIndexOfBirthDay);
        _result.sex = _cursor.getString(_cursorIndexOfSex);
        _result.height = _cursor.getDouble(_cursorIndexOfHeight);
        _result.weight = _cursor.getDouble(_cursorIndexOfWeight);
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
