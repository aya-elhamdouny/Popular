package com.example.popularmovies.Movies;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


@Entity
public class Movies implements Serializable {


   @SerializedName("id")
   @PrimaryKey
    int mId;
    @SerializedName("vote_count")
    int mVote_count;
   @SerializedName("vote_average")
   double mVote_average;
   @SerializedName( "title")
   String mTitel;
   @SerializedName("popularity")
   double mPopularity;
   @SerializedName("poster_path")
   String mPoster_path;
   @SerializedName("original_title")
   String mOriginal;
    @SerializedName("backdrop_path")
    String mBackdrop;
    @SerializedName("overview")
    String mOverview ;
    @SerializedName("release_date")
    String mRelease_date;

    public int getmId() {
        return mId;
    }
    public double getmVote_average() {
        return mVote_average;
    }
    public String getmTitel() {
        return mTitel;
    }
    public String getmPoster_path() {
        return "https://image.tmdb.org/t/p/w185/"+mPoster_path;
    }
    public String getmOriginal() {
        return mOriginal;
    }
    public String getmOverview() {
        return mOverview;
    }
    public String getmRelease_date() {
        return mRelease_date;
    }











}
