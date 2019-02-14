/**
 * 
 */
package com.metacube.tms.resource;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.metacube.tms.entity.Employee;

/**
 * Rest End point testing using HTTP Client
 * 
 * @author Yash Sharma<yash.sharma@metacube.com>
 * @since 21-Sep-2018
 */
public class EmployeeResourceIT {

	public static final String REST_SERVICE_URI = "http://localhost:8080/tms/api/v1/employees";

	/* GET */
	@SuppressWarnings("unchecked")
	private static void listAllEmployees() {
		System.out.println("Testing listAllEmployees API-----------");

		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap<String, Object>> employeesMap = restTemplate.getForObject(REST_SERVICE_URI + "/employee/",
				List.class);

		if (employeesMap != null) {
			for (LinkedHashMap<String, Object> map : employeesMap) {
				System.out.println("Employee : id=" + map.get("id") + ", Name=" + map.get("name"));
				;
			}
		} else {
			System.out.println("No employee exist----------");
		}
	}

	/* GET */
	private static void getEmployee() {
		System.out.println("Testing getEmployee API----------");
		RestTemplate restTemplate = new RestTemplate();
		Employee employee = restTemplate.getForObject(REST_SERVICE_URI + "/employee/1", Employee.class);
		System.out.println(employee);
	}

	/* POST */
	private static void createEmployee() {
		System.out.println("Testing create Employee API----------");
		RestTemplate restTemplate = new RestTemplate();
		Employee employee = new Employee();
		URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/employee/", employee, Employee.class);
		System.out.println("Location : " + uri.toASCIIString());
	}

	/* PUT */
	private static void updateEmployee() {
		System.out.println("Testing update Employee API----------");
		RestTemplate restTemplate = new RestTemplate();
		Employee employee = new Employee();
		restTemplate.put(REST_SERVICE_URI + "/employee/1", employee);
		System.out.println(employee);
	}

	/* DELETE */
	private static void deleteEmployee() {
		System.out.println("Testing delete Employee API----------");
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(REST_SERVICE_URI + "/employee/3");
	}

	public static void main(String args[]) {
		listAllEmployees();
		getEmployee();
		createEmployee();
		listAllEmployees();
		updateEmployee();
		listAllEmployees();
		deleteEmployee();
		listAllEmployees();
	}

}
