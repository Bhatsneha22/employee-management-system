package com.sneha.employee_management_backend.exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long id) {
        super("Employee with ID " + id + " not found");
    }

    public EmployeeNotFoundException(String message) {
        super(message);
    }
}