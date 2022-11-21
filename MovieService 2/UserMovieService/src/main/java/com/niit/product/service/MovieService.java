package com.niit.product.service;

import com.niit.product.exception.MovieAlreadyExists;
import com.niit.product.exception.UserNotFoundException;
import com.niit.product.exception.MovieNotFoundException;
import com.niit.product.model.Movie;
import com.niit.product.model.User;

import java.util.List;

public interface MovieService {
    User registerNewUser(User user);
    boolean saveUserMovie(Movie movie, String userName) throws UserNotFoundException, MovieAlreadyExists;
    boolean deleteMovieOfAUser(int movieId, String userName) throws UserNotFoundException, MovieNotFoundException;
    List<Movie> getAllMoviesOfAUser(String userName) throws UserNotFoundException;
    List<Movie> searchMovieByName(String userName, String name) throws UserNotFoundException;
    List<Movie> sortingSequence(String userName, String sortBy);
    boolean updateWatchedMovie(Movie movie, String userName);
}
