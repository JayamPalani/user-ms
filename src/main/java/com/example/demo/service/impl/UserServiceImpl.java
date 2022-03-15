package com.example.demo.service.impl;

import static com.example.demo.common.Constants.ERROR;
import static com.example.demo.common.Constants.INVALID_REQUEST;

import java.util.Date;
import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDAO;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.transformer.UserDTO;
import com.example.demo.transformer.UserRegisterRequest;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserDAO userDao;
  
  
  private final CacheManager cacheManager;
	@Override
	public User saveUser(UserRegisterRequest request) {
		User user=User.builder().userName(request.getUserName()).userPassword(request.getUserPassword()).
				userEmail(request.getUserEmail()).lastLoginDate(new Date()).build();
		return userDao.save(user);
	}
	
	@Cacheable(key = "'users'", value = "users")
	@Override
	public List<UserDTO> fetchAllUser() {
		return userDao.fethAllUser();
	}
	
	@Override
	public UserDTO checkLogin(UserRegisterRequest request) {
		UserDTO dto=null;
		User user=userDao.findUserByLogin(request.getUserEmail(),request.getUserPassword());
		if(null !=user) {
			dto=new UserDTO(user.getUserName(), user.getUserEmail(), user.getLastLoginDate());
			user.setLastLoginDate(new Date());
			userDao.save(user);
		}else {
			dto=new UserDTO();
			dto.setMessageCode(ERROR);
			dto.setMessage(INVALID_REQUEST);
		}
		return dto;
	}
	
	@CacheEvict(value = "users", allEntries = true)
	@Override
	public void deleteUserById(Long id) {
		 userDao.deleteByUserId(id);
	}
	
	@Cacheable(key = "#id", value = "user")
	@Override
	public User findUserById(Long id) {
		return userDao.findByUserId(id);
	}

}
