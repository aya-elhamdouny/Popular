package com.example.popularmovies.Movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailaerRespones {
    @SerializedName("results")
    List<Trialaer> uResult ;

    public List<Trialaer> getuResult() {
        return uResult;
    }

}
