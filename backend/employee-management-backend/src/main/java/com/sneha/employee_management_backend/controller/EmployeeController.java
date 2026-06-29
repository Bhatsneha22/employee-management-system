package com.sneha.employee_management_backend.controller;


import com.sneha.employee_management_backend.entity.Employee;
import com.sneha.employee_management_backend.service.EmployeeService;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sneha.employee_management_backend.dto.EmployeeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.sneha.employee_management_backend.dto.CreateEmployeeRequest;
import com.sneha.employee_management_backend.dto.EmployeeResponse;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Management", description = "APIs for managing employees")
public class EmployeeController {
    @PostConstruct
    public void init() {
        System.out.println("EmployeeController Loaded");
    }


    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Add Employee
@Operation(
    summary = "Create Employee",
    description = "Creates a new employee in the database"
)
@PostMapping
public EmployeeResponse saveEmployee(@Valid @RequestBody CreateEmployeeRequest request) {
    return employeeService.saveEmployee(request);
}

    // Get All Employees
@Operation(
    summary = "Get All Employees",
    description = "Returns all employees"
)
@GetMapping
public List<EmployeeResponse> getAllEmployees() {
    return employeeService.getAllEmployees();
}
@Operation(
    summary = "Get Employees with Pagination",
    description = "Returns employees using pagination and sorting"
)
@GetMapping("/page")
public Page<EmployeeResponse> getEmployees(Pageable pageable) {
    return employeeService.getEmployees(pageable);
}
@Operation(
    summary = "Filter Employees",
    description = "Returns employees from a specific department"
)
@GetMapping("/department")
public List<EmployeeResponse> getEmployeesByDepartment(
        @RequestParam String department) {

    return employeeService.getEmployeesByDepartment(department);
}
@Operation(
    summary = "Search Employees",
    description = "Search employees by first name"
)
@GetMapping("/search")
public List<EmployeeResponse> searchEmployeesByFirstName(
        @RequestParam String firstName) {

    return employeeService.searchEmployeesByFirstName(firstName);
}
@Operation(
    summary = "Get Employee By ID",
    description = "Returns an employee using the employee ID"
)
 @GetMapping("/{id}")
public EmployeeResponse getEmployeeById(@PathVariable Long id) {
    return employeeService.getEmployeeById(id);
}
@Operation(
    summary = "Update Employee",
    description = "Updates an existing employee"
)
   @PutMapping("/{id}")
public EmployeeResponse updateEmployee(
        @PathVariable Long id,
        @Valid @RequestBody CreateEmployeeRequest request) {

    return employeeService.updateEmployee(id, request);
}
@Operation(
    summary = "Delete Employee",
    description = "Deletes an employee using employee ID"
)
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {

    employeeService.deleteEmployee(id);

    return ResponseEntity.noContent().build();
}
}
