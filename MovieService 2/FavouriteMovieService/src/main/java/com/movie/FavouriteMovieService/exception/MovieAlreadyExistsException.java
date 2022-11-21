package com.movie.FavouriteMovieService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT,reason = "Movie Already Exsits")
public class MovieAlreadyExistsException extends Exception {
}
