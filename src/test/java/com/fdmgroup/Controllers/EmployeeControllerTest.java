package com.fdmgroup.Controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.Models.Employee;
import com.fdmgroup.Service.EmployeeService;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private EmployeeService employeeService;

	@Autowired
	private ObjectMapper objectMapper;



	
	
	@Test
	void getAllEmployees_returns_all_employees() throws Exception {
		List<Employee> mockEmployees = new ArrayList<>();
		mockEmployees.add(new Employee("Min", "Park", "min@fdm.com"));
		mockEmployees.add(new Employee("Don", "Witcombe", "don@fdm.com"));

		when(employeeService.getAllEmployees()).thenReturn(mockEmployees);

		mockMvc.perform(get("/api/employees/all"))
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(mockEmployees)));
		
		//The content().json() method takes a JSON string as an argument and compares it with the JSON content of the response received from the server. 
		//It allows you to verify whether the expected JSON structure and values match the actual response.
	}
	
    
	@Test
	void getEmployee_returns_single_employee() throws Exception {
        Employee employee = new Employee("Min", "Park", "min@fdm.com");

		when(employeeService.getEmployee(1L)).thenReturn(Optional.of(employee));
		
		mockMvc.perform(get("/api/employees/find/{id}", "1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Min"))
                .andExpect(jsonPath("$.lastName").value("Park"))
                .andExpect(jsonPath("$.emailId").value("min@fdm.com"));
				
		verify(employeeService, times(1)).getEmployee(1L);
	}

	// This issue
	// https://stackoverflow.com/questions/62560356/spring-boot-mockmvctest-mockhttpservletresponse-always-returns-empty-body
	// TLDR: Needed to provide equals method in employee entity
	@Test
	void createEmployee_creates_employee() throws Exception {
		Employee employee = new Employee("Min", "Park", "min@fdm.com");

		when(employeeService.createEmployee(employee)).thenReturn(employee);

		mockMvc.perform(post("/api/employees/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(employee)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstName").value("Min"))
				.andExpect(jsonPath("$.lastName").value("Park"))
				.andExpect(jsonPath("$.emailId").value("min@fdm.com"));
	}
	
	@Test
	void updateEmployee_updates_employee() throws Exception {
		Employee employeeUpdated = new Employee("MinUpdated", "Park", "min@fdm.com");
		
		when(employeeService.updateEmployee(employeeUpdated)).thenReturn(employeeUpdated);
		
		mockMvc.perform(put("/api/employees/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(employeeUpdated)))
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(employeeUpdated)))
				.andExpect(jsonPath("$.firstName").value("MinUpdated"))
				.andExpect(jsonPath("$.lastName").value("Park"))
				.andExpect(jsonPath("$.emailId").value("min@fdm.com"));
	}
	
	@Test
	void deleteEmployee_deletes_employee() throws Exception {
		mockMvc.perform(delete("/api/employees/delete/{id}", "1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		verify(employeeService, times(1)).deleteEmployee(1L);
	}
	
	
	
	
	
	
	
	
	
	
	

}
