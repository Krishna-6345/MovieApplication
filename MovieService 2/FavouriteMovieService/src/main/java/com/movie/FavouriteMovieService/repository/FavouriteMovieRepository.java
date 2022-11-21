package com.movie.FavouriteMovieService.repository;


import com.movie.FavouriteMovieService.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouriteMovieRepository extends MongoRepository<User,String>{

}
