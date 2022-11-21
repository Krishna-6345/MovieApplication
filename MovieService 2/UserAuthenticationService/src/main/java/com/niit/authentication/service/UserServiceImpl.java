package com.niit.authentication.service;

import com.niit.authentication.exception.UserNotFoundException;
import com.niit.authentication.model.User;
import com.niit.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User saveUser(User user){
        return userRepository.save(user);
    }

    @Override
    public User findUserByUserNameAndPassword(String userName, String password) throws UserNotFoundException {
        User user = userRepository.findUserByUserNameAndPassword(userName,password);
        if(user == null){
            throw new UserNotFoundException();
        }
        return user;
    }
}
