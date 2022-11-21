package com.movie.FavouriteMovieService.domain;

import com.movie.FavouriteMovieService.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class WatchedDTO {
    String userName;
    Movie movie;
}
