package com.example.popularmovies.Movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesRespons {


    @SerializedName("results")
    List<Movies> mResults  ;


    public List<Movies> getmResults() {
        return mResults;
    }
}
