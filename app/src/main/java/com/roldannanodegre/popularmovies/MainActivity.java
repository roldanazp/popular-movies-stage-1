package com.roldannanodegre.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roldannanodegre.popularmovies.adapters.MoviesAdapter;
import com.roldannanodegre.popularmovies.adapters.MoviesAdapter.MovieItemListener;
import com.roldannanodegre.popularmovies.api.TheMovieAPI;
import com.roldannanodegre.popularmovies.databinding.ActivityMainBinding;
import com.roldannanodegre.popularmovies.model.Movie;
import com.roldannanodegre.popularmovies.model.QueryResult;

import com.roldannanodegre.popularmovies.api.ApiConstants;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MovieItemListener, Callback<QueryResult>{

    private static final int SPAN_COUNT = 3;

    private int page = 1;
    private MoviesAdapter moviesAdapter;
    private QueryResult queryResults;
    private TheMovieAPI restClient;
    private MenuItem item;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUi();
        initRestClient();

    }

    private void initUi(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.rvMovies.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        moviesAdapter = new MoviesAdapter(MainActivity.this);
        binding.rvMovies.setAdapter(moviesAdapter);
    }

    private void initRestClient(){

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        restClient = retrofit.create(TheMovieAPI.class);

    }

    private void fetchMovies(MenuItem item, int page){
        binding.pbMovies.setVisibility(View.VISIBLE);
        binding.tvLoadingProblem.setVisibility(View.GONE);

        switch(item.getItemId()){
            case R.id.popularity:
                restClient.fetchPopularMovies(ApiConstants.THE_MOVIE_API_KEY, page).enqueue(this);
                break;
            case R.id.vote_average:
                restClient.fetchVoteAverageMovies(ApiConstants.THE_MOVIE_API_KEY, page).enqueue(this);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movies, menu);
        fetchMovies(item = menu.findItem(R.id.popularity), page);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        fetchMovies(this.item = item, page = 1);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(@NonNull Call<QueryResult> call, @NonNull Response<QueryResult> response) {
        binding.pbMovies.setVisibility(View.GONE);

        if(page == 1){
            moviesAdapter.clear();
        }

        QueryResult movies = response.body();
        if(movies!=null){
            queryResults = response.body();
            moviesAdapter.addAll(movies.results);
        }
    }

    @Override
    public void onFailure(@NonNull Call<QueryResult> call, @NonNull Throwable t) {
        moviesAdapter.clear();
        binding.pbMovies.setVisibility(View.GONE);
        binding.tvLoadingProblem.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMovieSelected(Movie movie) {
        Intent movieDetailIntent = new Intent(this, MovieDetailActivity.class);
        movieDetailIntent.putExtra(Movie.KEY, movie);
        startActivity(movieDetailIntent);
    }

    @Override
    public void loadMovies() {
        if(queryResults.total_pages > page && binding.pbMovies.getVisibility() != View.VISIBLE){
            fetchMovies(item, ++page);
        }
    }

}
