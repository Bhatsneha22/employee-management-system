package com.sneha.employee_management_backend.service;


import com.sneha.employee_management_backend.entity.Employee;
import com.sneha.employee_management_backend.exception.EmployeeNotFoundException;
import com.sneha.employee_management_backend.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import com.sneha.employee_management_backend.dto.EmployeeDTO;
import com.sneha.employee_management_backend.mapper.EmployeeMapper;
import com.sneha.employee_management_backend.dto.CreateEmployeeRequest;
import com.sneha.employee_management_backend.dto.EmployeeResponse;
import java.util.stream.Collectors;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Service
public class EmployeeService {
    private static final Logger logger =
        LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

  public EmployeeResponse saveEmployee(CreateEmployeeRequest request) {
    logger.info("Saving employee with email: {}", request.getEmail());
    Employee employee = EmployeeMapper.toEntity(request);

    Employee savedEmployee = employeeRepository.save(employee);
    logger.info("Employee saved successfully.");
    return EmployeeMapper.toResponse(savedEmployee);
}

   public List<EmployeeResponse> getAllEmployees() {

    return employeeRepository.findAll()
            .stream()
            .map(EmployeeMapper::toResponse)
            .toList();
   }
   public List<EmployeeResponse> searchEmployeesByFirstName(String firstName) {
    logger.info("Searching employees with name: {}", firstName);
    return employeeRepository
            .findByFirstNameContainingIgnoreCase(firstName)
            .stream()
            .map(EmployeeMapper::toResponse)
            .toList();
}
   public EmployeeResponse getEmployeeById(Long id) {
    logger.info("Fetching employee with ID: {}", id);
    Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException(id));
    logger.info("Employee found with ID: {}", id);
    return EmployeeMapper.toResponse(employee);
}
public EmployeeResponse updateEmployee(Long id, CreateEmployeeRequest request) {
    
    logger.info("Updating employee with ID: {}", id);
    Employee existingEmployee = employeeRepository.findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException(id));

    existingEmployee.setFirstName(request.getFirstName());
    existingEmployee.setLastName(request.getLastName());
    existingEmployee.setEmail(request.getEmail());
    existingEmployee.setPhoneNumber(request.getPhoneNumber());
    existingEmployee.setDepartment(request.getDepartment());
    existingEmployee.setSalary(request.getSalary());
    existingEmployee.setJoiningDate(request.getJoiningDate());

    Employee updatedEmployee = employeeRepository.save(existingEmployee);
    logger.info("Employee updated successfully with ID: {}", id);
    return EmployeeMapper.toResponse(updatedEmployee);
}
public void deleteEmployee(Long id) {
    logger.info("Deleting employee with ID: {}", id);
    Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new EmployeeNotFoundException(id));

employeeRepository.delete(employee);
logger.info("Employee deleted successfully with ID: {}", id);
}
public Page<EmployeeResponse> getEmployees(Pageable pageable) {

    return employeeRepository
            .findAll(pageable)
            .map(EmployeeMapper::toResponse);
}
public List<EmployeeResponse> getEmployeesByDepartment(String department) {
    logger.info("Fetching employees from department: {}", department);
    return employeeRepository
            .findByDepartmentIgnoreCase(department)
            .stream()
            .map(EmployeeMapper::toResponse)
            .toList();
}
}