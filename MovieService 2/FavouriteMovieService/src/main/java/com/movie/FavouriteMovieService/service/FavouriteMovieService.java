package com.movie.FavouriteMovieService.service;

import com.movie.FavouriteMovieService.exception.MovieAlreadyExistsException;
import com.movie.FavouriteMovieService.exception.MovieNotFoundException;
import com.movie.FavouriteMovieService.model.Movie;

import java.util.List;
import java.util.Optional;

public interface FavouriteMovieService {

    boolean addMovie(Movie movie, String userName) throws MovieNotFoundException, MovieAlreadyExistsException;
    boolean deleteMovie(String userName, int movieId) throws MovieNotFoundException;
    List<Movie> getAllWatchedMovies(String userName);
    List<Movie> searchByName(String userName, String prefix);
    List<Movie> sortingSequence(String userName, String sortBy);

}
