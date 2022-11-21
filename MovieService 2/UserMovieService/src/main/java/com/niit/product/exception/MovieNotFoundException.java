package com.niit.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND,reason = "Movie List is Empty")
public class MovieNotFoundException extends Exception{
}
