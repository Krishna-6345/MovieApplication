package com.niit.authentication.service;

import com.niit.authentication.model.User;

import java.util.Map;

public interface SecurityTokenGenerator {
    Map<String,String> generateToken(User user);
}
