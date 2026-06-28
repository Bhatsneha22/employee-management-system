package com.sneha.employee_management_backend.controller;


import com.sneha.employee_management_backend.entity.Employee;
import com.sneha.employee_management_backend.service.EmployeeService;

import jakarta.annotation.PostConstruct;

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
    public Employee saveEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    // Get All Employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
    return employeeService.getEmployeeById(id);
}
    @PutMapping("/{id}")
public Employee updateEmployee(@PathVariable Long id,
                               @RequestBody Employee employee) {

    return employeeService.updateEmployee(id, employee);
}
@DeleteMapping("/{id}")
public String deleteEmployee(@PathVariable Long id) {
    employeeService.deleteEmployee(id);
    return "Employee deleted successfully!";
}
}
