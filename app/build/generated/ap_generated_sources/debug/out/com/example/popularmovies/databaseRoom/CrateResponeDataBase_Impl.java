package com.example.popularmovies.databaseRoom;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.example.popularmovies.Movies.RoomDatabaseDao;
import com.example.popularmovies.Movies.RoomDatabaseDao_Impl;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public final class CrateResponeDataBase_Impl extends CrateResponeDataBase {
  private volatile RoomDatabaseDao _roomDatabaseDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Movies` (`mId` INTEGER NOT NULL, `mVote_count` INTEGER NOT NULL, `mVote_average` REAL NOT NULL, `mTitel` TEXT, `mPopularity` REAL NOT NULL, `mPoster_path` TEXT, `mOriginal` TEXT, `mBackdrop` TEXT, `mOverview` TEXT, `mRelease_date` TEXT, PRIMARY KEY(`mId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"cfbadf63e206d135c93bc377c8e7c03b\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `Movies`");
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
        final HashMap<String, TableInfo.Column> _columnsMovies = new HashMap<String, TableInfo.Column>(10);
        _columnsMovies.put("mId", new TableInfo.Column("mId", "INTEGER", true, 1));
        _columnsMovies.put("mVote_count", new TableInfo.Column("mVote_count", "INTEGER", true, 0));
        _columnsMovies.put("mVote_average", new TableInfo.Column("mVote_average", "REAL", true, 0));
        _columnsMovies.put("mTitel", new TableInfo.Column("mTitel", "TEXT", false, 0));
        _columnsMovies.put("mPopularity", new TableInfo.Column("mPopularity", "REAL", true, 0));
        _columnsMovies.put("mPoster_path", new TableInfo.Column("mPoster_path", "TEXT", false, 0));
        _columnsMovies.put("mOriginal", new TableInfo.Column("mOriginal", "TEXT", false, 0));
        _columnsMovies.put("mBackdrop", new TableInfo.Column("mBackdrop", "TEXT", false, 0));
        _columnsMovies.put("mOverview", new TableInfo.Column("mOverview", "TEXT", false, 0));
        _columnsMovies.put("mRelease_date", new TableInfo.Column("mRelease_date", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMovies = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMovies = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMovies = new TableInfo("Movies", _columnsMovies, _foreignKeysMovies, _indicesMovies);
        final TableInfo _existingMovies = TableInfo.read(_db, "Movies");
        if (! _infoMovies.equals(_existingMovies)) {
          throw new IllegalStateException("Migration didn't properly handle Movies(com.example.popularmovies.Movies.Movies).\n"
                  + " Expected:\n" + _infoMovies + "\n"
                  + " Found:\n" + _existingMovies);
        }
      }
    }, "cfbadf63e206d135c93bc377c8e7c03b", "8f90ae74dae042dec3857a19e04d6257");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "Movies");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `Movies`");
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
  public RoomDatabaseDao getData() {
    if (_roomDatabaseDao != null) {
      return _roomDatabaseDao;
    } else {
      synchronized(this) {
        if(_roomDatabaseDao == null) {
          _roomDatabaseDao = new RoomDatabaseDao_Impl(this);
        }
        return _roomDatabaseDao;
      }
    }
  }
}
