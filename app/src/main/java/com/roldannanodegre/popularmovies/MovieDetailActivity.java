package com.roldannanodegre.popularmovies;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.roldannanodegre.popularmovies.api.ImageUrl;
import com.roldannanodegre.popularmovies.databinding.ActivityMovieDetailBinding;
import com.roldannanodegre.popularmovies.model.Movie;
import com.roldannanodegre.popularmovies.util.ImageUtil;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        Movie movie = (Movie) getIntent().getSerializableExtra(Movie.KEY);
        setTitle(movie.title);
        binding.setMovie(movie);
        ImageUtil.setImage(binding.imageLayout.ivMovieImage, ImageUrl.W342, movie.posterPath);
    }

}
