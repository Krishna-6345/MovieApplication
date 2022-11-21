package com.movie.FavouriteMovieService.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class Movie {

    @Id
    int movieId;
    String overView;
    float popularity;
    String releaseDate;
    String title;
    float voteAverage;
    String posterPath;
    boolean watched;

}
