package com.niit.product.service;

import com.niit.product.config.Producer;
import com.niit.product.domain.WatchedDTO;
import com.niit.product.exception.MovieAlreadyExists;
import com.niit.product.exception.UserNotFoundException;
import com.niit.product.exception.MovieNotFoundException;
import com.niit.product.model.Movie;
import com.niit.product.model.User;
import com.niit.product.proxy.UserProxy;
import com.niit.product.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;
    private UserProxy userProxy;

    @Autowired
    Producer producer;

    @Autowired
    public MovieServiceImpl(UserProxy userProxy, MovieRepository movieRepository){
        this.movieRepository = movieRepository;
        this.userProxy = userProxy;
    }

    @Override
    public User registerNewUser(User user){
        userProxy.saveUser(user);
        user.getMovies().clear();
        user.getWatched().clear();
        movieRepository.save(user);
        return user;
    }

    @Override
    public boolean saveUserMovie(Movie movie, String userName) throws UserNotFoundException, MovieAlreadyExists {
        if(movieRepository.findById(userName).isEmpty()){
            throw new UserNotFoundException();
        }
        User customer= movieRepository.findById(userName).get();
        List<Movie> customerMovie =customer.getMovies();
        if(customerMovie.stream().anyMatch(obj -> movie.getMovieId() == obj.getMovieId())){
            throw new MovieAlreadyExists();
        }
        customerMovie.add(movie);
        customer.setMovies(customerMovie);
        movieRepository.save(customer);
        return true;
    }

    @Override
    public boolean deleteMovieOfAUser(int movieId, String userName) throws UserNotFoundException, MovieNotFoundException {
        User user = movieRepository.findById(userName).get();
        List<Movie> movieList = user.getMovies();
        Movie movie = movieList.stream()
                .filter(obj -> movieId==obj.getMovieId())
                .findAny().orElse(null);
        if(!movieList.contains(movie)){
            throw new MovieNotFoundException();
        }
        movieList.remove(movie);
        user.setMovies(movieList);
        movieRepository.save(user);
        return true;

    }

    @Override
    public List<Movie> getAllMoviesOfAUser(String userName) throws UserNotFoundException {
        movieRepository.findById(userName).get();
        User customer= movieRepository.findById(userName).get();
        return customer.getMovies();
    }

    @Override
    public List<Movie> searchMovieByName(String userName, String name) {
        User user=movieRepository.findById(userName).get();
        List<Movie> allMovies=user.getMovies();
        List<Movie> searched=allMovies.stream().filter(movie -> movie.getTitle().startsWith(name)).collect(Collectors.toList());
        return searched;
    }

    @Override
    public List<Movie> sortingSequence(String userName, String sortBy) {
        User user= movieRepository.findById(userName).get();
        List<Movie> movies =user.getMovies();
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

    @Override
    public boolean updateWatchedMovie(Movie movie, String userName) {
        WatchedDTO watchedDTO = new WatchedDTO(userName,movie);
        producer.sendMessageToRabbitTemplate(watchedDTO);
        return true;
    }
}
