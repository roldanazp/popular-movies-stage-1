package com.roldannanodegre.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable {

    public static final String KEY = Movie.class.getName();


    @SerializedName("vote_count")
    public int voteCount;

    public long id;

    boolean video;

    @SerializedName("vote_average")
    public double voteAverage;

    public String title;

    public double popularity;

    @SerializedName("poster_path")
    public String posterPath;

    @SerializedName("original_language")
    public String originalLanguage;

    @SerializedName("original_title")
    public String originalTitle;

    @SerializedName("genre_ids")
    public List<Integer> genreIds;

    @SerializedName("backdrop_path")
    public String backdropPath;

    public boolean adult;

    public String overview;

    @SerializedName("release_date")
    public String releaseDate;

}
