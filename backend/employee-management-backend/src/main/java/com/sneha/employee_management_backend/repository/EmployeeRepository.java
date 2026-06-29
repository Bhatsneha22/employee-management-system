package com.sneha.employee_management_backend.repository;
import java.util.List;
import com.sneha.employee_management_backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByFirstNameContainingIgnoreCase(String firstName);
    List<Employee> findByDepartmentIgnoreCase(String department);
}