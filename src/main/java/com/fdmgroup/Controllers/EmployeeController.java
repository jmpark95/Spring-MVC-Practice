package com.fdmgroup.Controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.Models.Employee;
import com.fdmgroup.Service.EmployeeService;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/employees")
public class EmployeeController {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	private final EmployeeService employeeService;
	
	@Autowired
	//as of spring boot 2, Autowired not needed for a single constructor, but required if there are multiple constructors
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	
	

	@GetMapping("/all")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> allEmployees = employeeService.getAllEmployees();
		return ResponseEntity.status(HttpStatus.OK).body(allEmployees);
	}
	
	@PostMapping("/create")
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
		Employee newEmployee = employeeService.createEmployee(employee);
		return ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
	}
	
}




//logger.trace("This is a TRACE level message");
//logger.debug("This is a DEBUG level message");
//logger.info("This is an INFO level message");
//logger.warn("This is a WARN level message");
//logger.error("This is an ERROR level message");
//        logger.info("{}", allEmployees);