package com.sneha.employee_management_backend.repository;

import com.sneha.employee_management_backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}