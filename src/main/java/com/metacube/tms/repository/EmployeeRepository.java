package com.metacube.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.metacube.tms.entity.Employee;

/**
 * @author Yash Sharma<yash.sharma@metacube.com>
 * @since 15-Sep-2018
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	public Iterable<Employee> findByName(String name);

    @Query("SELECT emp FROM Employee emp where name = :name")
    List<Employee> fetchByCustomQuery(@Param("name") String name);
}
