package com.example.popularmovies.Movies;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("author")
    String mAuthor;
    @SerializedName("content")
    String mContent;

    public String getmAuthor() {
        return mAuthor;
    }
    public String getmContent() {
        return mContent;
    }
}
