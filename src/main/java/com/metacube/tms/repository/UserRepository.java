/**
 * 
 */
package com.metacube.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metacube.tms.entity.User;

/**
 * @author Yash Sharma<yash.sharma@metacube.com>
 * @since 21-Sep-2018
 */
public interface UserRepository extends JpaRepository<User, Integer> {

	/**
	 * 
	 * @param firstName
	 * @return
	 */
	public Iterable<User> findByFirstName(String firstName);

}
