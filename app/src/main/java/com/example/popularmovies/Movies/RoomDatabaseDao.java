package com.example.popularmovies.Movies;


import android.graphics.Movie;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.popularmovies.Movies.Movies;

import java.util.List;

@Dao
public interface RoomDatabaseDao {
    @Query("SELECT * FROM Movies")
    LiveData<List<Movies>> getAllMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMovie(Movies movie);

    @Delete
    void delete(Movies movie);

    @Query("SELECT * FROM Movies where mId = :id")
    List<Movies> CheckMovies(int id);
}
