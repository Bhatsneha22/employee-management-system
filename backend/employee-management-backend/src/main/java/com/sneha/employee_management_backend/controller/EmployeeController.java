package com.sneha.employee_management_backend.controller;


import com.sneha.employee_management_backend.entity.Employee;
import com.sneha.employee_management_backend.service.EmployeeService;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
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
    @PostMapping
public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody Employee employee) {

    Employee savedEmployee = employeeService.saveEmployee(employee);

    return ResponseEntity.status(HttpStatus.CREATED)
                         .body(savedEmployee);
}

    // Get All Employees
    @GetMapping
public ResponseEntity<List<Employee>> getAllEmployees() {

    return ResponseEntity.ok(employeeService.getAllEmployees());
}
    @GetMapping("/{id}")
public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {

    Employee employee = employeeService.getEmployeeById(id);

    return ResponseEntity.ok(employee);
}
   @PutMapping("/{id}")
public ResponseEntity<Employee> updateEmployee(
        @PathVariable Long id,
        @Valid @RequestBody Employee employee) {

    Employee updatedEmployee = employeeService.updateEmployee(id, employee);

    return ResponseEntity.ok(updatedEmployee);
}
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {

    employeeService.deleteEmployee(id);

    return ResponseEntity.noContent().build();
}
}
