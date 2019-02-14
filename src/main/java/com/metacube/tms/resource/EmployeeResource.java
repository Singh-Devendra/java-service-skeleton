package com.metacube.tms.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metacube.tms.entity.Employee;
import com.metacube.tms.exception.ResourceNotFoundException;
import com.metacube.tms.modal.CurrentUser;
import com.metacube.tms.modal.EmployeeModal;
import com.metacube.tms.service.EmployeeService;
import com.metacube.tms.util.CollectionUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Yash Sharma<yash.sharma@metacube.com>
 * @since 15-Sep-2018
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/employees")
@Api("Set of endpoints for Creating, Retrieving, Updating and Deleting Employees.")
public class EmployeeResource {
	
	//TODO Use Modal objects only in request and response
	
	@Autowired
	private EmployeeService employeeService;

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/list")
	@ApiOperation("Returns list of all Employees in the system.")
	public ResponseEntity<Collection<EmployeeModal>> getEmployeesList(@AuthenticationPrincipal CurrentUser currentUser) throws IOException {
		currentUser.getOrganizationID();
		Collection<Employee> employees = CollectionUtils.iterableToCollection(employeeService.getAllEmployees());
		if (employees.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
		Collection<EmployeeModal> employeesModal = new ArrayList<EmployeeModal>();
		employees.forEach(employee -> {
			EmployeeModal employeeModal = new EmployeeModal();
			//BeanUtils.copyProperties(employees, employeeModal); //FIXME not copying ATM
			employeeModal.setId(employee.getId());
			employeeModal.setName(employee.getName());
			employeesModal.add(employeeModal);
		});
		
		return new ResponseEntity<Collection<EmployeeModal>>(employeesModal, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/{id}")
    @ApiOperation("Returns a specific employee by their identifier. 404 if does not exist.")
	public ResponseEntity<?> getEmployeeById(
			@ApiParam("Id of the employee to be obtained. Cannot be empty.") @PathVariable("id") long id)
			throws IOException {
		
		//// log.info("Fetching Employee with id {}", id);
		/*Optional<Employee> employee = employeeService.getEmployeeById(id);
        if (!employee.isPresent()) {
            // log.error("Employee with id {} not found.", id);
            return new ResponseEntity(new ResourceNotFoundException("Employee", "id", id), HttpStatus.NOT_FOUND);
        }*/
		return new ResponseEntity<Employee>(employeeService.getEmployeeById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id)), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param employee
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/")
	@ApiOperation("Creates a new employee.")
	public ResponseEntity<?> createEmployee(
			@ApiParam("Employee information to be created.") @RequestBody EmployeeModal employeeModal)
			throws IOException {
		//// log.info("Creating Employee : {}", employeeModal);
		
		//TODO Move this bo -> do conversion to Service
		Employee employee = new Employee();
		//employee.setId(employeeModal.getId());
		employee.setName(employeeModal.getName());
		if (employeeService.isEmployeeExist(employee)) {
            //// log.error("Unable to create. An Employee with name {} already exist", employee.getName());
            return new ResponseEntity(new ResourceNotFoundException("Unable to create. An Employee with name " + employee.getName() + " already exist."), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Employee>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
	}
	
	/**
	 * 
	 * @param id
	 * @param employee
	 * @return
	 * @throws IOException
	 */
	@PutMapping("/{id}")
	@ApiOperation("Creates a new employee.")
	public ResponseEntity<?> updateEmployee(
			@ApiParam("Id of the employee to be updated. Cannot be empty.") @PathVariable("id") Long id,
			@ApiParam("Employee information to be updated. 404 if the employee's identifier is not found.") @RequestBody Employee employee)
			throws IOException {
		//// log.info("Updating Employee with id {}", id);
		 
		Optional<Employee> currentEmployee = employeeService.getEmployeeById(id);
        if (!currentEmployee.isPresent()) {
            // log.error("Unable to update. Employee with id {} not found.", id);
            return new ResponseEntity(new ResourceNotFoundException("Unable to upate. Employee with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }
        Employee employeeModal = currentEmployee.get();
        employeeModal.setName(employee.getName());
 
		return new ResponseEntity<Employee>(employeeService.saveEmployee(employeeModal), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	@DeleteMapping("/{id}")
    @ApiOperation("Deletes an employee from the system. 404 if the employee's identifier is not found.")
	public ResponseEntity<Employee> deleteEmployee(
			@ApiParam("Id of the employee to be deleted. Cannot be empty.") @PathVariable("id") Long id)
			throws IOException {
		// log.info("Fetching & Deleting Employee with id {}", id);

		Optional<Employee> currentEmployee = employeeService.getEmployeeById(id);
		
        if (!currentEmployee.isPresent()) {
            // log.error("Unable to delete. Employee with id {} not found.", id);
            return new ResponseEntity(new ResourceNotFoundException("Unable to delete. Employee with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
		
	}
	
}