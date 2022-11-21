package com.niit.authentication.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.niit.authentication.exception.UserNotFoundException;
import com.niit.authentication.model.User;
import com.niit.authentication.service.UserServiceImpl;
import com.niit.authentication.service.SecurityTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    SecurityTokenGenerator securityTokenGenerator;

    @PostMapping("/login")
    @HystrixCommand(fallbackMethod = "fallbackLogin",commandKey = "loginKey",groupKey = "login")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1000")
    public ResponseEntity<?> login(@RequestBody User user) throws UserNotFoundException {
        ResponseEntity<?> responseEntity;
        try {
            User newUser = userService.findUserByUserNameAndPassword(user.getUserName(), user.getPassword());
            if(newUser.getUserName().equals(user.getUserName())){
                Map<String,String> tokenMap = securityTokenGenerator.generateToken(newUser);
                responseEntity=new ResponseEntity<>(tokenMap, HttpStatus.OK);
            }
            else {
                responseEntity=new ResponseEntity<>("Invalid User",HttpStatus.NOT_FOUND);
            }
        }
        catch (UserNotFoundException e){
            throw new UserNotFoundException();
        }
        catch (Exception e){
            responseEntity=new ResponseEntity<>("Other error occured",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    public ResponseEntity<?> fallbackLogin(@RequestBody User user) throws UserNotFoundException {
        String msg = "login failed";
        return new ResponseEntity<>(msg,HttpStatus.GATEWAY_TIMEOUT);
    }

    @PostMapping("/save")
    public ResponseEntity<User> save(@RequestBody User user){
        userService.saveUser(user);
        return new ResponseEntity<User>(user,HttpStatus.CREATED);
    }
}
