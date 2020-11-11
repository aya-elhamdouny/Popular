package com.example.popularmovies.databaseRoom;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.popularmovies.Movies.Movies;
import com.example.popularmovies.Movies.RoomDatabaseDao;


@Database(entities = {Movies.class},version = 1 , exportSchema = false)
public abstract class CrateResponeDataBase extends RoomDatabase {

    private static final String LOG_TAG = CrateResponeDataBase.class.getSimpleName();
    private static final String DATABASE_NAME = "Moviesapp";
    private static  CrateResponeDataBase sInstance;
    public abstract RoomDatabaseDao getData();
    public static CrateResponeDataBase getInstance(Context context) {
        if (sInstance == null) {

                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        CrateResponeDataBase.class, DATABASE_NAME)
                        .build();

        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }


}
