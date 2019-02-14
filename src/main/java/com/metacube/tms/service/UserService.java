/**
 * 
 */
package com.metacube.tms.service;

import java.util.Optional;

import com.metacube.tms.entity.User;

/**
 * @author Yash Sharma<yash.sharma@metacube.com>
 * @since 21-Sep-2018
 */
public interface UserService extends BaseService {

	/**
	 * 
	 * @return
	 */
	public Iterable<User> findAllUsers();
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Optional<User> findById(Integer id);

	/**
	 * 
	 * @param name
	 * @return
	 */
	public Iterable<User> findByName(String name);

	/**
	 * 
	 * @param user
	 * @return
	 */
	public User saveUser(User user);

	/**
	 * 
	 * @param id
	 */
	public void deleteUserById(Integer id);

	/**
	 * 
	 * @param user
	 * @return
	 */
	public boolean isUserExist(User user);
}
