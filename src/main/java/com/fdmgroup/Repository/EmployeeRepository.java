package com.fdmgroup.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.Models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
