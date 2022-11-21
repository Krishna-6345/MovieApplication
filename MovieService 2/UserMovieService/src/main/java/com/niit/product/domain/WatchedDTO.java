package com.niit.product.domain;

import com.niit.product.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class WatchedDTO {
    String userName;
    Movie movie;
}
