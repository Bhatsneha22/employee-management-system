package com.sneha.employee_management_backend.service;


import com.sneha.employee_management_backend.entity.Employee;
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
    return employeeRepository.findById(id).orElse(null);
}
    public Employee updateEmployee(Long id, Employee updatedEmployee) {

    Employee employee = employeeRepository.findById(id).orElse(null);

    if (employee != null) {

        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setPhoneNumber(updatedEmployee.getPhoneNumber());
        employee.setDepartment(updatedEmployee.getDepartment());
        employee.setSalary(updatedEmployee.getSalary());
        employee.setJoiningDate(updatedEmployee.getJoiningDate());

        return employeeRepository.save(employee);
    }

    return null;
}
public void deleteEmployee(Long id) {
    employeeRepository.deleteById(id);
}
}