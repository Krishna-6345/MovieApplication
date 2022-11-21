package com.movie.FavouriteMovieService.controller;


import com.movie.FavouriteMovieService.exception.MovieAlreadyExistsException;
import com.movie.FavouriteMovieService.exception.MovieNotFoundException;
import com.movie.FavouriteMovieService.model.Movie;
import com.movie.FavouriteMovieService.service.FavouriteMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/v3")
public class FavouriteMovieController {

    ResponseEntity responseEntity;

    private FavouriteMovieService favouriteMovieService;

    @Autowired
    public FavouriteMovieController(FavouriteMovieService favouriteMovieService) {
        this.favouriteMovieService = favouriteMovieService;
    }

    @GetMapping("/authorized/watchedmovies/{userId}")
    public ResponseEntity<?> getAllWatchedMovies(@PathVariable String userId) {
        return new ResponseEntity<>(favouriteMovieService.getAllWatchedMovies(userId), HttpStatus.OK);
    }

    @GetMapping("/authorized/searchWatchedMovies/{userId}/{prefix}")
    public ResponseEntity<?> searchWatchedMovies(@PathVariable String userId,@PathVariable String prefix) {
        return new ResponseEntity<>(favouriteMovieService.searchByName(userId, prefix), HttpStatus.OK);
    }

    @GetMapping("/authorized/getSortedFavMovies/{userName}/{sortBy}")
    public ResponseEntity<?> getMoviesOfAUserInSequence(@PathVariable String userName, @PathVariable String sortBy) {
        List<Movie> movieList = favouriteMovieService.sortingSequence(userName, sortBy);
        return new ResponseEntity<>(movieList,HttpStatus.OK);
    }


    @PutMapping("/authorized/addWatched/{userId}")
    public ResponseEntity<?> addTask(@RequestBody Movie movie, @PathVariable String userId) throws MovieNotFoundException, MovieAlreadyExistsException {

        favouriteMovieService.addMovie(movie, userId);

        return new ResponseEntity<>("Task added", HttpStatus.OK);
    }

    @DeleteMapping("/authorized/deleteWatched/{userId}/{movieId}")
    public ResponseEntity<?> deleteMovie(@PathVariable("userId") String userId, @PathVariable("movieId") int movieId) throws MovieNotFoundException {
        favouriteMovieService.deleteMovie(userId, movieId);
        return new ResponseEntity<>("Movie Deleted", HttpStatus.OK);
    }
}
