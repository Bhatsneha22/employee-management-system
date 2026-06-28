package com.sneha.employee_management_backend.service;


import com.sneha.employee_management_backend.entity.Employee;
import com.sneha.employee_management_backend.exception.EmployeeNotFoundException;
import com.sneha.employee_management_backend.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    public Employee getEmployeeById(Long id) {

    return employeeRepository.findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException(id));
}
public Employee updateEmployee(Long id, Employee updatedEmployee) {

    Employee existingEmployee = employeeRepository.findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException(id));

    existingEmployee.setFirstName(updatedEmployee.getFirstName());
    existingEmployee.setLastName(updatedEmployee.getLastName());
    existingEmployee.setEmail(updatedEmployee.getEmail());
    existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());
    existingEmployee.setDepartment(updatedEmployee.getDepartment());
    existingEmployee.setSalary(updatedEmployee.getSalary());
    existingEmployee.setJoiningDate(updatedEmployee.getJoiningDate());

    return employeeRepository.save(existingEmployee);
}
public void deleteEmployee(Long id) {
   Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new EmployeeNotFoundException(id));

employeeRepository.delete(employee);
}
}