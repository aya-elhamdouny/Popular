package com.example.popularmovies.Movies;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.lifecycle.ComputableLiveData;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker.Observer;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public final class RoomDatabaseDao_Impl implements RoomDatabaseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfMovies;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfMovies;

  public RoomDatabaseDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMovies = new EntityInsertionAdapter<Movies>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `Movies`(`mId`,`mVote_count`,`mVote_average`,`mTitel`,`mPopularity`,`mPoster_path`,`mOriginal`,`mBackdrop`,`mOverview`,`mRelease_date`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Movies value) {
        stmt.bindLong(1, value.mId);
        stmt.bindLong(2, value.mVote_count);
        stmt.bindDouble(3, value.mVote_average);
        if (value.mTitel == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.mTitel);
        }
        stmt.bindDouble(5, value.mPopularity);
        if (value.mPoster_path == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.mPoster_path);
        }
        if (value.mOriginal == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.mOriginal);
        }
        if (value.mBackdrop == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.mBackdrop);
        }
        if (value.mOverview == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.mOverview);
        }
        if (value.mRelease_date == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.mRelease_date);
        }
      }
    };
    this.__deletionAdapterOfMovies = new EntityDeletionOrUpdateAdapter<Movies>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Movies` WHERE `mId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Movies value) {
        stmt.bindLong(1, value.mId);
      }
    };
  }

  @Override
  public void addMovie(Movies movie) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfMovies.insert(movie);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(Movies movie) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfMovies.handle(movie);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Movies>> getAllMovies() {
    final String _sql = "SELECT * FROM Movies";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Movies>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<Movies> compute() {
        if (_observer == null) {
          _observer = new Observer("Movies") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfMId = _cursor.getColumnIndexOrThrow("mId");
          final int _cursorIndexOfMVoteCount = _cursor.getColumnIndexOrThrow("mVote_count");
          final int _cursorIndexOfMVoteAverage = _cursor.getColumnIndexOrThrow("mVote_average");
          final int _cursorIndexOfMTitel = _cursor.getColumnIndexOrThrow("mTitel");
          final int _cursorIndexOfMPopularity = _cursor.getColumnIndexOrThrow("mPopularity");
          final int _cursorIndexOfMPosterPath = _cursor.getColumnIndexOrThrow("mPoster_path");
          final int _cursorIndexOfMOriginal = _cursor.getColumnIndexOrThrow("mOriginal");
          final int _cursorIndexOfMBackdrop = _cursor.getColumnIndexOrThrow("mBackdrop");
          final int _cursorIndexOfMOverview = _cursor.getColumnIndexOrThrow("mOverview");
          final int _cursorIndexOfMReleaseDate = _cursor.getColumnIndexOrThrow("mRelease_date");
          final List<Movies> _result = new ArrayList<Movies>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Movies _item;
            _item = new Movies();
            _item.mId = _cursor.getInt(_cursorIndexOfMId);
            _item.mVote_count = _cursor.getInt(_cursorIndexOfMVoteCount);
            _item.mVote_average = _cursor.getDouble(_cursorIndexOfMVoteAverage);
            _item.mTitel = _cursor.getString(_cursorIndexOfMTitel);
            _item.mPopularity = _cursor.getDouble(_cursorIndexOfMPopularity);
            _item.mPoster_path = _cursor.getString(_cursorIndexOfMPosterPath);
            _item.mOriginal = _cursor.getString(_cursorIndexOfMOriginal);
            _item.mBackdrop = _cursor.getString(_cursorIndexOfMBackdrop);
            _item.mOverview = _cursor.getString(_cursorIndexOfMOverview);
            _item.mRelease_date = _cursor.getString(_cursorIndexOfMReleaseDate);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public List<Movies> CheckMovies(int id) {
    final String _sql = "SELECT * FROM Movies where mId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfMId = _cursor.getColumnIndexOrThrow("mId");
      final int _cursorIndexOfMVoteCount = _cursor.getColumnIndexOrThrow("mVote_count");
      final int _cursorIndexOfMVoteAverage = _cursor.getColumnIndexOrThrow("mVote_average");
      final int _cursorIndexOfMTitel = _cursor.getColumnIndexOrThrow("mTitel");
      final int _cursorIndexOfMPopularity = _cursor.getColumnIndexOrThrow("mPopularity");
      final int _cursorIndexOfMPosterPath = _cursor.getColumnIndexOrThrow("mPoster_path");
      final int _cursorIndexOfMOriginal = _cursor.getColumnIndexOrThrow("mOriginal");
      final int _cursorIndexOfMBackdrop = _cursor.getColumnIndexOrThrow("mBackdrop");
      final int _cursorIndexOfMOverview = _cursor.getColumnIndexOrThrow("mOverview");
      final int _cursorIndexOfMReleaseDate = _cursor.getColumnIndexOrThrow("mRelease_date");
      final List<Movies> _result = new ArrayList<Movies>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Movies _item;
        _item = new Movies();
        _item.mId = _cursor.getInt(_cursorIndexOfMId);
        _item.mVote_count = _cursor.getInt(_cursorIndexOfMVoteCount);
        _item.mVote_average = _cursor.getDouble(_cursorIndexOfMVoteAverage);
        _item.mTitel = _cursor.getString(_cursorIndexOfMTitel);
        _item.mPopularity = _cursor.getDouble(_cursorIndexOfMPopularity);
        _item.mPoster_path = _cursor.getString(_cursorIndexOfMPosterPath);
        _item.mOriginal = _cursor.getString(_cursorIndexOfMOriginal);
        _item.mBackdrop = _cursor.getString(_cursorIndexOfMBackdrop);
        _item.mOverview = _cursor.getString(_cursorIndexOfMOverview);
        _item.mRelease_date = _cursor.getString(_cursorIndexOfMReleaseDate);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
