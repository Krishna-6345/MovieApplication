package com.niit.authentication.service;

import com.niit.authentication.exception.UserNotFoundException;
import com.niit.authentication.model.User;

public interface UserService {

    public User saveUser(User user);
    public User findUserByUserNameAndPassword(String userName, String password) throws UserNotFoundException;
}
