package com.example.popularmovies.Movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewRespons {


    @SerializedName("results")
    List<Review> rResult ;
    public List<Review> getrResult() {
        return rResult;
    }
}
