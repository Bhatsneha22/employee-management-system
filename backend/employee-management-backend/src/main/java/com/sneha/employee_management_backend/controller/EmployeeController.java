package com.sneha.employee_management_backend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sneha.employee_management_backend.dto.CreateEmployeeRequest;
import com.sneha.employee_management_backend.dto.EmployeeResponse;
import com.sneha.employee_management_backend.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Management", description = "APIs for managing employees")
public class EmployeeController {

    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
        logger.info("EmployeeController initialized");
    }

    @Operation(
            summary = "Create Employee",
            description = "Creates a new employee in the database"
    )
    @PostMapping
    public EmployeeResponse saveEmployee(
            @Valid @RequestBody CreateEmployeeRequest request) {

        logger.info("Creating employee with email: {}", request.getEmail());

        EmployeeResponse response = employeeService.saveEmployee(request);

        logger.info("Employee created successfully");

        return response;
    }

    @Operation(
            summary = "Get All Employees",
            description = "Returns all employees"
    )
    @GetMapping
    public List<EmployeeResponse> getAllEmployees() {

        logger.info("Fetching all employees");

        return employeeService.getAllEmployees();
    }

    @Operation(
            summary = "Get Employees with Pagination",
            description = "Returns employees using pagination and sorting"
    )
    @GetMapping("/page")
    public Page<EmployeeResponse> getEmployees(Pageable pageable) {

        logger.info("Fetching employees with pagination");

        return employeeService.getEmployees(pageable);
    }

    @Operation(
            summary = "Filter Employees",
            description = "Returns employees from a specific department"
    )
    @GetMapping("/department")
    public List<EmployeeResponse> getEmployeesByDepartment(
            @RequestParam String department) {

        logger.info("Fetching employees from department: {}", department);

        return employeeService.getEmployeesByDepartment(department);
    }

    @Operation(
            summary = "Search Employees",
            description = "Search employees by first name"
    )
    @GetMapping("/search")
    public List<EmployeeResponse> searchEmployeesByFirstName(
            @RequestParam String firstName) {

        logger.info("Searching employees with first name: {}", firstName);

        return employeeService.searchEmployeesByFirstName(firstName);
    }

    @Operation(
            summary = "Get Employee By ID",
            description = "Returns an employee using the employee ID"
    )
    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable Long id) {

        logger.info("Fetching employee with ID: {}", id);

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

        logger.info("Updating employee with ID: {}", id);

        EmployeeResponse response =
                employeeService.updateEmployee(id, request);

        logger.info("Employee updated successfully with ID: {}", id);

        return response;
    }

    @Operation(
            summary = "Delete Employee",
            description = "Deletes an employee using employee ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {

        logger.info("Deleting employee with ID: {}", id);

        employeeService.deleteEmployee(id);

        logger.info("Employee deleted successfully with ID: {}", id);

        return ResponseEntity.noContent().build();
    }
}