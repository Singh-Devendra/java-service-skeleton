/**
 * 
 */
package com.metacube.tms.service.impl;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metacube.tms.entity.User;
import com.metacube.tms.repository.UserRepository;
import com.metacube.tms.service.UserService;
import com.metacube.tms.util.CollectionUtils;

/**
 * @author Yash Sharma<yash.sharma@metacube.com>
 * @since 21-Sep-2018
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Iterable<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> findById(Integer id) {
		return userRepository.findById(id);
	}

	@Override
	public Iterable<User> findByName(String name) {
		return userRepository.findByFirstName(name);
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public void deleteUserById(Integer id) {
		userRepository.deleteById(id);
	}

	@Override
	public boolean isUserExist(User user) {
		Collection<User> users = CollectionUtils.iterableToCollection(userRepository.findByFirstName(user.getFirstName()));
		return !users.isEmpty();
	}

}
