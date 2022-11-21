package com.niit.authentication.repository;

import com.niit.authentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    User findUserByUserNameAndPassword(String userName, String password);

}
