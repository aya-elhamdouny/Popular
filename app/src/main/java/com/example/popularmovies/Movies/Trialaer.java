package com.example.popularmovies.Movies;

import com.google.gson.annotations.SerializedName;

public  class Trialaer {


    @SerializedName("key")
    String mkey ;

    @SerializedName("name")
    String mName_Trailaer;
    public String getmName_Trailaer() {
        return mName_Trailaer;
    }
    public String getMkey() {
        return mkey;
    }

}
