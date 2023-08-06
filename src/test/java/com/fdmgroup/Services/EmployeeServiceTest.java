package com.fdmgroup.Services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.Models.Employee;
import com.fdmgroup.Repository.EmployeeRepository;
import com.fdmgroup.Service.EmployeeService;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
	@InjectMocks
	EmployeeService employeeService;
	
	@Mock
	EmployeeRepository employeeRepository;
	

	
	
	
	@Test
	void getEmployee_should_return_single_employee() {
		Employee employee = new Employee("Min", "Park", "min@fdm.com");
		
		when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		
		assertEquals(Optional.of(employee), employeeService.getEmployee(1L));
		
		verify(employeeRepository, times(1)).findById(1L);
	}
	
	@Test
	void non_existing_getEmployee_should_return_empty() {		
		when(employeeRepository.findById(9999L)).thenReturn(Optional.empty());
		
		assertThat(employeeService.getEmployee(9999L).isEmpty());
		
		verify(employeeRepository, times(1)).findById(9999L);
	}
	
	@Test
	void getAllEmployees_should_return_all_employees() {
		List<Employee> allEmployees = new ArrayList<>();
		allEmployees.add(new Employee("Min", "Park", "min@fdm.com"));
		allEmployees.add(new Employee("Don", "Witcombe", "don@fdm.com"));
		allEmployees.add(new Employee("Stanley", "Chilton", "stan@fdm.com"));
		
		when(employeeRepository.findAll()).thenReturn(allEmployees);
		
		assertEquals(allEmployees, employeeService.getAllEmployees());
		
		verify(employeeRepository, times(1)).findAll();
	}
	
	@Test
	void createEmployee_should_create_single_employee() {
		Employee employee = new Employee("Min", "Park", "min@fdm.com");
		
		when(employeeRepository.save(employee)).thenReturn(employee);
		
		assertEquals(employee, employeeService.createEmployee(employee));
		
		verify(employeeRepository, times(1)).save(employee);
	}
	
	@Test
	void updateEmployee_should_update_single_employee() {
		Employee updatedEmployee = new Employee("MinUpdated", "Park", "min@fdm.com");
		
		when(employeeRepository.save(updatedEmployee)).thenReturn(updatedEmployee);
		
		assertEquals(updatedEmployee, employeeService.updateEmployee(updatedEmployee));
		
		verify(employeeRepository, times(1)).save(updatedEmployee);
	}
	
	@Test
	void deleteEmployee_should_delete_single_employee() {
        doNothing().when(employeeRepository).deleteById(1L);

		employeeService.deleteEmployee(1L);
		
        verify(employeeRepository, times(1)).deleteById(1L);
	}


}

