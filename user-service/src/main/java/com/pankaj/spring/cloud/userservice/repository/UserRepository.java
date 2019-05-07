package com.pankaj.spring.cloud.userservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.pankaj.spring.cloud.userservice.domain.User;


public interface UserRepository extends CrudRepository<User, Long>{
			User findByUserNameAndPassword(String userName, String password);
			User findByUserName(String userName);
}
