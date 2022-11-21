package com.niit.product.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@Data
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
