/**
 * 
 */
package com.metacube.tms.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metacube.tms.entity.Employee;
import com.metacube.tms.repository.EmployeeRepository;
import com.metacube.tms.service.EmployeeService;
import com.metacube.tms.util.CollectionUtils;

/**
 * @author Yash Sharma<yash.sharma@metacube.com>
 * @since 21-Sep-2018
 */
@Service
public class EmployeeServiceImpl extends BaseServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Iterable<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}
	
	@Override
	public Optional<Employee> getEmployeeById(Long empId) {
		return employeeRepository.findById(empId);
	}
	
	@Override
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public Iterable<Employee> saveEmployees(List<Employee> employees) {
		return employeeRepository.saveAll(employees);
	}
	
	@Override
	public boolean isEmployeeExist(Employee employee) {
		Collection<Employee> employees = CollectionUtils.iterableToCollection(employeeRepository.findByName(employee.getName()));
		return !employees.isEmpty();
	}

	@Override
	public void deleteEmployeeById(Long id) {
		employeeRepository.deleteById(id);
	}

}
