package com.movie.FavouriteMovieService.service;

import com.movie.FavouriteMovieService.exception.MovieAlreadyExistsException;
import com.movie.FavouriteMovieService.exception.MovieNotFoundException;
import com.movie.FavouriteMovieService.model.Movie;
import com.movie.FavouriteMovieService.model.User;
import com.movie.FavouriteMovieService.repository.FavouriteMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FavouriteMovieServiceImpl implements FavouriteMovieService {

    @Autowired
    FavouriteMovieRepository favouriteMovieRepository;

    @Override
    public boolean addMovie(Movie movie, String userName) throws MovieNotFoundException, MovieAlreadyExistsException {

        if(favouriteMovieRepository.findById(userName).isEmpty()){
            throw new MovieNotFoundException();
        }
        User user = favouriteMovieRepository.findById(userName).get();
        List<Movie> movies = user.getWatched();
        if(movies.stream().anyMatch(obj -> movie.getMovieId() == obj.getMovieId())){
            throw new MovieAlreadyExistsException();
        }
        movie.setWatched(true);
        movies.add(movie);
        user.setWatched(movies);
        favouriteMovieRepository.save(user);
        return true;
    }

    @Override
    public boolean deleteMovie(String userName, int movieId) throws MovieNotFoundException {
        User user = favouriteMovieRepository.findById(userName).get();
        List<Movie> movies = user.getWatched();
        Movie movie = movies.stream()
                .filter(obj -> movieId==obj.getMovieId())
                .findAny().orElse(null);
        if(movie == null || !movies.contains(movie)){
            throw new MovieNotFoundException();
        }
        movies.remove(movie);
        user.setWatched(movies);
        favouriteMovieRepository.save(user);
        return true;
    }

    @Override
    public List<Movie> getAllWatchedMovies(String userName) {

        User customer= favouriteMovieRepository.findById(userName).get();
        return customer.getWatched();
    }

    @Override
    public List<Movie> searchByName(String userName, String prefix) {
        User customer= favouriteMovieRepository.findById(userName).get();
        List<Movie> customerMovie =customer.getWatched();
        return customerMovie.stream().filter(movie -> movie.getTitle().startsWith(prefix)).collect(Collectors.toList());
    }

    @Override
    public List<Movie> sortingSequence(String userName, String sortBy) {
        User user= favouriteMovieRepository.findById(userName).get();
        List<Movie> movies =user.getWatched();
        switch (sortBy) {
            case "Title":
                movies.sort(Comparator.comparing(Movie::getTitle));
                return movies;
            case "Popularity":
                movies.sort(Comparator.comparing(Movie::getPopularity));
                return movies;
            case "Votes":
                movies.sort(Comparator.comparing(Movie::getVoteAverage));
                return movies;
            default:
                return movies;
        }
    }
}
