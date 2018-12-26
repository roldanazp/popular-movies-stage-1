package com.roldannanodegre.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QueryResult {

    public int page;

    @SerializedName("total_results")
    public int total_results;

    @SerializedName("total_pages")
    public int total_pages;

    public List<Movie> results;

}
