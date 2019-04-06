package com.example.bleh.myapplication.DB;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class AppDatabase_Impl extends AppDatabase {
  private volatile UserDao _userDao;

  private volatile FoodDao _foodDao;

  private volatile ExerciseDao _exerciseDao;

  private volatile PlanDao _planDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `User` (`uid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `first_name` TEXT, `last_name` TEXT, `username` TEXT, `password` TEXT, `birthDay` INTEGER NOT NULL, `sex` TEXT, `height` REAL NOT NULL, `weight` REAL NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Food` (`foodID` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `food_name` TEXT, `food_type` TEXT, `calories` REAL NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Exercise` (`exerciseId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `exercise_name` TEXT, `met` REAL NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Plan` (`planid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `uid` INTEGER NOT NULL, `planDistribution` REAL NOT NULL, `workOutSessionDuration` INTEGER NOT NULL, `type` TEXT, `nbOfDays` INTEGER NOT NULL, `amount` REAL NOT NULL, `progress` INTEGER NOT NULL, `bmr` REAL NOT NULL, `workoutPerWeek` INTEGER NOT NULL, `currentWeight` REAL NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"f85f9deedd9179a6c11a48f0b8e01c52\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `User`");
        _db.execSQL("DROP TABLE IF EXISTS `Food`");
        _db.execSQL("DROP TABLE IF EXISTS `Exercise`");
        _db.execSQL("DROP TABLE IF EXISTS `Plan`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsUser = new HashMap<String, TableInfo.Column>(9);
        _columnsUser.put("uid", new TableInfo.Column("uid", "INTEGER", true, 1));
        _columnsUser.put("first_name", new TableInfo.Column("first_name", "TEXT", false, 0));
        _columnsUser.put("last_name", new TableInfo.Column("last_name", "TEXT", false, 0));
        _columnsUser.put("username", new TableInfo.Column("username", "TEXT", false, 0));
        _columnsUser.put("password", new TableInfo.Column("password", "TEXT", false, 0));
        _columnsUser.put("birthDay", new TableInfo.Column("birthDay", "INTEGER", true, 0));
        _columnsUser.put("sex", new TableInfo.Column("sex", "TEXT", false, 0));
        _columnsUser.put("height", new TableInfo.Column("height", "REAL", true, 0));
        _columnsUser.put("weight", new TableInfo.Column("weight", "REAL", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUser = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUser = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUser = new TableInfo("User", _columnsUser, _foreignKeysUser, _indicesUser);
        final TableInfo _existingUser = TableInfo.read(_db, "User");
        if (! _infoUser.equals(_existingUser)) {
          throw new IllegalStateException("Migration didn't properly handle User(com.example.bleh.myapplication.DB.User).\n"
                  + " Expected:\n" + _infoUser + "\n"
                  + " Found:\n" + _existingUser);
        }
        final HashMap<String, TableInfo.Column> _columnsFood = new HashMap<String, TableInfo.Column>(4);
        _columnsFood.put("foodID", new TableInfo.Column("foodID", "INTEGER", true, 1));
        _columnsFood.put("food_name", new TableInfo.Column("food_name", "TEXT", false, 0));
        _columnsFood.put("food_type", new TableInfo.Column("food_type", "TEXT", false, 0));
        _columnsFood.put("calories", new TableInfo.Column("calories", "REAL", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFood = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFood = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFood = new TableInfo("Food", _columnsFood, _foreignKeysFood, _indicesFood);
        final TableInfo _existingFood = TableInfo.read(_db, "Food");
        if (! _infoFood.equals(_existingFood)) {
          throw new IllegalStateException("Migration didn't properly handle Food(com.example.bleh.myapplication.DB.Food).\n"
                  + " Expected:\n" + _infoFood + "\n"
                  + " Found:\n" + _existingFood);
        }
        final HashMap<String, TableInfo.Column> _columnsExercise = new HashMap<String, TableInfo.Column>(3);
        _columnsExercise.put("exerciseId", new TableInfo.Column("exerciseId", "INTEGER", true, 1));
        _columnsExercise.put("exercise_name", new TableInfo.Column("exercise_name", "TEXT", false, 0));
        _columnsExercise.put("met", new TableInfo.Column("met", "REAL", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysExercise = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesExercise = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoExercise = new TableInfo("Exercise", _columnsExercise, _foreignKeysExercise, _indicesExercise);
        final TableInfo _existingExercise = TableInfo.read(_db, "Exercise");
        if (! _infoExercise.equals(_existingExercise)) {
          throw new IllegalStateException("Migration didn't properly handle Exercise(com.example.bleh.myapplication.DB.Exercise).\n"
                  + " Expected:\n" + _infoExercise + "\n"
                  + " Found:\n" + _existingExercise);
        }
        final HashMap<String, TableInfo.Column> _columnsPlan = new HashMap<String, TableInfo.Column>(11);
        _columnsPlan.put("planid", new TableInfo.Column("planid", "INTEGER", true, 1));
        _columnsPlan.put("uid", new TableInfo.Column("uid", "INTEGER", true, 0));
        _columnsPlan.put("planDistribution", new TableInfo.Column("planDistribution", "REAL", true, 0));
        _columnsPlan.put("workOutSessionDuration", new TableInfo.Column("workOutSessionDuration", "INTEGER", true, 0));
        _columnsPlan.put("type", new TableInfo.Column("type", "TEXT", false, 0));
        _columnsPlan.put("nbOfDays", new TableInfo.Column("nbOfDays", "INTEGER", true, 0));
        _columnsPlan.put("amount", new TableInfo.Column("amount", "REAL", true, 0));
        _columnsPlan.put("progress", new TableInfo.Column("progress", "INTEGER", true, 0));
        _columnsPlan.put("bmr", new TableInfo.Column("bmr", "REAL", true, 0));
        _columnsPlan.put("workoutPerWeek", new TableInfo.Column("workoutPerWeek", "INTEGER", true, 0));
        _columnsPlan.put("currentWeight", new TableInfo.Column("currentWeight", "REAL", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPlan = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPlan = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPlan = new TableInfo("Plan", _columnsPlan, _foreignKeysPlan, _indicesPlan);
        final TableInfo _existingPlan = TableInfo.read(_db, "Plan");
        if (! _infoPlan.equals(_existingPlan)) {
          throw new IllegalStateException("Migration didn't properly handle Plan(com.example.bleh.myapplication.DB.Plan).\n"
                  + " Expected:\n" + _infoPlan + "\n"
                  + " Found:\n" + _existingPlan);
        }
      }
    }, "f85f9deedd9179a6c11a48f0b8e01c52", "5bbcaf2880c899581a51e95178290091");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "User","Food","Exercise","Plan");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `User`");
      _db.execSQL("DELETE FROM `Food`");
      _db.execSQL("DELETE FROM `Exercise`");
      _db.execSQL("DELETE FROM `Plan`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public FoodDao foodDao() {
    if (_foodDao != null) {
      return _foodDao;
    } else {
      synchronized(this) {
        if(_foodDao == null) {
          _foodDao = new FoodDao_Impl(this);
        }
        return _foodDao;
      }
    }
  }

  @Override
  public ExerciseDao exerciseDao() {
    if (_exerciseDao != null) {
      return _exerciseDao;
    } else {
      synchronized(this) {
        if(_exerciseDao == null) {
          _exerciseDao = new ExerciseDao_Impl(this);
        }
        return _exerciseDao;
      }
    }
  }

  @Override
  public PlanDao planDao() {
    if (_planDao != null) {
      return _planDao;
    } else {
      synchronized(this) {
        if(_planDao == null) {
          _planDao = new PlanDao_Impl(this);
        }
        return _planDao;
      }
    }
  }
}
