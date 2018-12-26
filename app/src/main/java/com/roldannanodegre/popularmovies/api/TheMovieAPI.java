package com.roldannanodegre.popularmovies.api;

import com.roldannanodegre.popularmovies.model.QueryResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.roldannanodegre.popularmovies.api.ApiConstants.*;

public interface TheMovieAPI {

    @GET(POPULAR_MOVIE_PATH)
    Call<QueryResult> fetchPopularMovies(@Query(API_KEY) String apiKey,
                                         @Query(PAGE) int page);
    @GET(VOTE_AVERAGE_MOVIE_PATH)
    Call<QueryResult> fetchVoteAverageMovies(@Query(API_KEY) String apiKey,
                                             @Query(PAGE) int page);
}
