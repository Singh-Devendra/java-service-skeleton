package com.metacube.tms.service;

import java.util.List;
import java.util.Optional;

import com.metacube.tms.entity.Employee;

/**
 * @author Yash Sharma<yash.sharma@metacube.com>
 * @since 15-Sep-2018
 */
public interface EmployeeService extends BaseService {

	/**
	 * 
	 * @param employee
	 * @return
	 */
	public Employee saveEmployee(Employee employee);

	/**
	 * 
	 * @param employees
	 * @return
	 */
	public Iterable<Employee> saveEmployees(List<Employee> employees);

	/**
	 * 
	 * @return
	 */
	public Iterable<Employee> getAllEmployees();

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Employee> getEmployeeById(Long id);

	/**
	 * @param employee
	 * @return
	 */
	public boolean isEmployeeExist(Employee employee);

	/**
	 * @param id
	 */
	public void deleteEmployeeById(Long id);
}
