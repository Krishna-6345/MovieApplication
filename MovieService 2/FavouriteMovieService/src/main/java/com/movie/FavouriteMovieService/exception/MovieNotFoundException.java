package com.movie.FavouriteMovieService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT,reason = "Movie not found")
public class MovieNotFoundException extends Exception {

}
