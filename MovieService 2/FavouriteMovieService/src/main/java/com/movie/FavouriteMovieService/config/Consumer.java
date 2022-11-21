package com.movie.FavouriteMovieService.config;

import com.movie.FavouriteMovieService.domain.WatchedDTO;
import com.movie.FavouriteMovieService.service.FavouriteMovieServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @Autowired
    FavouriteMovieServiceImpl service;

    @RabbitListener(queues = "watched_queue")
    public void getCustomerFromRabbitMQ(WatchedDTO watchedDTO){
        try {
            service.addMovie(watchedDTO.getMovie(), watchedDTO.getUserName());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

