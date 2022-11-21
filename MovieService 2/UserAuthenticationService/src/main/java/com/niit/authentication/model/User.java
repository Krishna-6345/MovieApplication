package com.niit.authentication.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table
public class User {

    @Id
    String userName;
    String password;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(){}
}
