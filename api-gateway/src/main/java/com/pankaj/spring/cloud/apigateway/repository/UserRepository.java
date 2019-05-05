package com.pankaj.spring.cloud.apigateway.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pankaj.spring.cloud.apigateway.domain.User;


public interface UserRepository extends CrudRepository<User, Long>{
			User findByUserNameAndPassword(String userName, String password);
			User findByUserName(String userName);
}
