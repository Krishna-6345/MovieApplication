package com.niit.product.controller;

import com.niit.product.exception.MovieAlreadyExists;
import com.niit.product.exception.UserNotFoundException;
import com.niit.product.exception.MovieNotFoundException;
import com.niit.product.model.Movie;
import com.niit.product.model.User;
import com.niit.product.service.MovieServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@Slf4j
@RestController
@RequestMapping("/api/v2")
public class MovieController {
    @Autowired
    MovieServiceImpl movieService;

    @PostMapping("/register")
    public ResponseEntity<User> registerNewUser(@RequestBody User user){
        movieService.registerNewUser(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @PutMapping(value = "/authorized/savemovie/{userName}")
    public ResponseEntity<?> saveUserMovie(@RequestBody Movie movie, @PathVariable String userName) throws UserNotFoundException, MovieAlreadyExists {
        //log.info("status={}, customer={}, body={}", "SUCCESS", userName,  movie.toString());
        movieService.saveUserMovie(movie,userName);
        return new ResponseEntity<>("Saved",HttpStatus.OK);
    }

    @GetMapping("/authorized/getmovie/{userName}")
    public ResponseEntity<?> getAllMoviesOfAUser(@PathVariable String userName) throws UserNotFoundException {
        List<Movie> movieList = movieService.getAllMoviesOfAUser(userName);
        return new ResponseEntity<>(movieList,HttpStatus.OK);
    }

    @GetMapping("/authorized/getSeacrchedMovie/{userName}/{prefix}")
    public ResponseEntity<?> getSearchedMoviesOfAUser(@PathVariable String userName, @PathVariable String prefix) {
        List<Movie> movieList = movieService.searchMovieByName(userName, prefix);
        return new ResponseEntity<>(movieList,HttpStatus.OK);
    }

    @GetMapping("/authorized/getSortedMovies/{userName}/{sortBy}")
    public ResponseEntity<?> getMoviesOfAUserInSequence(@PathVariable String userName, @PathVariable String sortBy) {
        List<Movie> movieList = movieService.sortingSequence(userName, sortBy);
        return new ResponseEntity<>(movieList,HttpStatus.OK);
    }

    @PutMapping("/authorized/updateWatched/{userName}")
    public ResponseEntity<?> updateWatched2ndWay(@RequestBody Movie movie, @PathVariable("userName") String userName) throws MovieNotFoundException {
        if(movieService.updateWatchedMovie(movie,userName)) {
            return new ResponseEntity<>("movie is watched",HttpStatus.OK);
        }
        else return new ResponseEntity<>("error",HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/authorized/deletemovie/{userName}/{movieId}")
    public ResponseEntity<?> deleteMovieOfAUser(@PathVariable int movieId, @PathVariable String userName) throws UserNotFoundException, MovieNotFoundException {
        movieService.deleteMovieOfAUser(movieId, userName);
        return new ResponseEntity<>("Movie Deleted",HttpStatus.OK);
    }
}
