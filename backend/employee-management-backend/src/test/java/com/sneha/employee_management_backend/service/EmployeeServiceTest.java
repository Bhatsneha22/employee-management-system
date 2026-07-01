package com.sneha.employee_management_backend.service;

import java.util.Collections;
import com.sneha.employee_management_backend.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import com.sneha.employee_management_backend.dto.CreateEmployeeRequest;
import com.sneha.employee_management_backend.dto.EmployeeResponse;
import com.sneha.employee_management_backend.entity.Employee;
import com.sneha.employee_management_backend.exception.EmployeeNotFoundException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

	@Test
void saveEmployeeTest() {

    CreateEmployeeRequest request = new CreateEmployeeRequest();
    request.setFirstName("Sneha");
    request.setLastName("Bhat");
    request.setEmail("sneha@test.com");
    request.setPhoneNumber("9999999999");
    request.setDepartment("Engineering");
    request.setSalary(50000.0);
    request.setJoiningDate(LocalDate.now());

    Employee employee = new Employee();
    employee.setEmployeeId(1L);
    employee.setFirstName("Sneha");
    employee.setLastName("Bhat");
    employee.setEmail("sneha@test.com");
    employee.setPhoneNumber("9999999999");
    employee.setDepartment("Engineering");
    employee.setSalary(50000.0);
    employee.setJoiningDate(LocalDate.now());

    when(employeeRepository.save(any(Employee.class)))
            .thenReturn(employee);

    EmployeeResponse response = employeeService.saveEmployee(request);

    assertNotNull(response);
    assertEquals("Sneha", response.getFirstName());
    assertEquals("Engineering", response.getDepartment());
    assertEquals("sneha@test.com", response.getEmail());

    verify(employeeRepository).save(any(Employee.class));
}
   @Test
void getEmployeeByIdTest() {

    Employee employee = new Employee();

    employee.setEmployeeId(1L);
    employee.setFirstName("Sneha");
    employee.setLastName("Bhat");
    employee.setEmail("sneha@test.com");
    employee.setPhoneNumber("9999999999");
    employee.setDepartment("Engineering");
    employee.setSalary(50000.0);
    employee.setJoiningDate(LocalDate.now());

    when(employeeRepository.findById(1L))
            .thenReturn(Optional.of(employee));

    EmployeeResponse response = employeeService.getEmployeeById(1L);

    assertNotNull(response);
    assertEquals(1L, response.getEmployeeId());
    assertEquals("Sneha", response.getFirstName());
    assertEquals("Engineering", response.getDepartment());

    verify(employeeRepository).findById(1L);
}
@Test
void getEmployeeByIdNotFoundTest() {

    when(employeeRepository.findById(100L))
            .thenReturn(Optional.empty());

    EmployeeNotFoundException exception =
            assertThrows(EmployeeNotFoundException.class, () -> {
                employeeService.getEmployeeById(100L);
            });

    assertEquals(
            "Employee with ID 100 not found",
            exception.getMessage()
    );

    verify(employeeRepository).findById(100L);
}
@Test
void updateEmployeeTest() {

    // Existing employee
    Employee existingEmployee = new Employee();
    existingEmployee.setEmployeeId(1L);
    existingEmployee.setFirstName("Sneha");
    existingEmployee.setLastName("Bhat");
    existingEmployee.setEmail("sneha@test.com");
    existingEmployee.setPhoneNumber("9999999999");
    existingEmployee.setDepartment("Engineering");
    existingEmployee.setSalary(50000.0);
    existingEmployee.setJoiningDate(LocalDate.now());

    // Request
    CreateEmployeeRequest request = new CreateEmployeeRequest();
    request.setFirstName("Sneha Updated");
    request.setLastName("Bhat");
    request.setEmail("updated@test.com");
    request.setPhoneNumber("8888888888");
    request.setDepartment("IT");
    request.setSalary(70000.0);
    request.setJoiningDate(LocalDate.now());

    // Mock repository
    when(employeeRepository.findById(1L))
            .thenReturn(Optional.of(existingEmployee));

    when(employeeRepository.save(any(Employee.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

    // Call service
    EmployeeResponse response = employeeService.updateEmployee(1L, request);

    // Assertions
    assertNotNull(response);
    assertEquals(1L, response.getEmployeeId());
    assertEquals("Sneha Updated", response.getFirstName());
    assertEquals("Bhat", response.getLastName());
    assertEquals("updated@test.com", response.getEmail());
    assertEquals("IT", response.getDepartment());

    // Verify
    verify(employeeRepository).findById(1L);
    verify(employeeRepository).save(any(Employee.class));
}
@Test
void deleteEmployeeTest() {

    // Existing employee
    Employee employee = new Employee();

    employee.setEmployeeId(1L);
    employee.setFirstName("Sneha");
    employee.setLastName("Bhat");
    employee.setEmail("sneha@test.com");
    employee.setPhoneNumber("9999999999");
    employee.setDepartment("Engineering");
    employee.setSalary(50000.0);
    employee.setJoiningDate(LocalDate.now());

    // Mock repository
    when(employeeRepository.findById(1L))
            .thenReturn(Optional.of(employee));

    doNothing().when(employeeRepository).delete(employee);

    // Call service
    employeeService.deleteEmployee(1L);

    // Verify interactions
    verify(employeeRepository, times(1)).findById(1L);
    verify(employeeRepository, times(1)).delete(employee);
}
@Test
void getAllEmployeesEmptyTest() {

    when(employeeRepository.findAll())
            .thenReturn(Collections.emptyList());

    var employees = employeeService.getAllEmployees();

    assertNotNull(employees);
    assertTrue(employees.isEmpty());

    verify(employeeRepository).findAll();
}
@Test
void deleteEmployeeNotFoundTest() {

    when(employeeRepository.findById(100L))
            .thenReturn(Optional.empty());

    assertThrows(EmployeeNotFoundException.class,
            () -> employeeService.deleteEmployee(100L));

    verify(employeeRepository).findById(100L);
}
@Test
void updateEmployeeNotFoundTest() {

    CreateEmployeeRequest request = new CreateEmployeeRequest();

    request.setFirstName("Test");

    when(employeeRepository.findById(100L))
            .thenReturn(Optional.empty());

    assertThrows(EmployeeNotFoundException.class,
            () -> employeeService.updateEmployee(100L, request));

    verify(employeeRepository).findById(100L);
}
@Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

}