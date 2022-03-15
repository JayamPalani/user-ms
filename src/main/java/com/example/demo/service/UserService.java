package com.example.demo.service;

import java.util.List;

import com.example.demo.model.User;
import com.example.demo.transformer.UserDTO;
import com.example.demo.transformer.UserRegisterRequest;



public interface UserService {

	User saveUser(UserRegisterRequest request);

	List<UserDTO> fetchAllUser();

	UserDTO checkLogin(UserRegisterRequest request);

	void deleteUserById(Long id);
	

	User findUserById(Long id);

}
