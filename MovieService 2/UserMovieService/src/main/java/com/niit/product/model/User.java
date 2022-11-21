package com.niit.product.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class User {
    @Id
    String userName;
    String password;
    String emailId;
    String gender;
    List<Movie> movies;
    List<Movie> watched;

    public User(String userName, String password, String emailId, String gender, List<Movie> movies, List<Movie> watched) {
        this.userName = userName;
        this.password = password;
        this.emailId = emailId;
        this.gender = gender;
        this.movies = movies;
        this.watched=watched;
    }
}
