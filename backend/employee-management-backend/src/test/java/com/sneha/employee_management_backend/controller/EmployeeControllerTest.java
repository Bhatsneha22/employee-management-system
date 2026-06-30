package com.sneha.employee_management_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sneha.employee_management_backend.dto.CreateEmployeeRequest;
import com.sneha.employee_management_backend.dto.EmployeeResponse;
import com.sneha.employee_management_backend.service.EmployeeService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EmployeeService employeeService;

    @Test
    void contextLoads() {

    }

    @Test
    void getEmployeeByIdTest() throws Exception {

        EmployeeResponse response = new EmployeeResponse();

        response.setEmployeeId(1L);
        response.setFirstName("Sneha");
        response.setLastName("Bhat");
        response.setEmail("sneha@test.com");
        response.setDepartment("Engineering");
        response.setJoiningDate(LocalDate.now());

        when(employeeService.getEmployeeById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(1))
                .andExpect(jsonPath("$.firstName").value("Sneha"))
                .andExpect(jsonPath("$.lastName").value("Bhat"))
                .andExpect(jsonPath("$.email").value("sneha@test.com"))
                .andExpect(jsonPath("$.department").value("Engineering"));
    }

    @Test
    void getAllEmployeesTest() throws Exception {

        EmployeeResponse employee = new EmployeeResponse();

        employee.setEmployeeId(1L);
        employee.setFirstName("Sneha");
        employee.setLastName("Bhat");
        employee.setEmail("sneha@test.com");
        employee.setDepartment("Engineering");
        employee.setJoiningDate(LocalDate.now());

        when(employeeService.getAllEmployees())
                .thenReturn(List.of(employee));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeId").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Sneha"));
    }

    @Test
    void saveEmployeeTest() throws Exception {

        CreateEmployeeRequest request = new CreateEmployeeRequest();

        request.setFirstName("Sneha");
        request.setLastName("Bhat");
        request.setEmail("sneha@test.com");
        request.setPhoneNumber("9999999999");
        request.setDepartment("Engineering");
        request.setSalary(50000.0);
        request.setJoiningDate(LocalDate.now());

        EmployeeResponse response = new EmployeeResponse();

        response.setEmployeeId(1L);
        response.setFirstName("Sneha");
        response.setLastName("Bhat");
        response.setEmail("sneha@test.com");
        response.setDepartment("Engineering");
        response.setJoiningDate(LocalDate.now());

        when(employeeService.saveEmployee(any(CreateEmployeeRequest.class)))
        .thenReturn(response);

        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(1))
                .andExpect(jsonPath("$.firstName").value("Sneha"));
    }

    @Test
    void updateEmployeeTest() throws Exception {

        CreateEmployeeRequest request = new CreateEmployeeRequest();

        request.setFirstName("Sneha Updated");
        request.setLastName("Bhat");
        request.setEmail("updated@test.com");
        request.setPhoneNumber("8888888888");
        request.setDepartment("IT");
        request.setSalary(70000.0);
        request.setJoiningDate(LocalDate.now());

        EmployeeResponse response = new EmployeeResponse();

        response.setEmployeeId(1L);
        response.setFirstName("Sneha Updated");
        response.setLastName("Bhat");
        response.setEmail("updated@test.com");
        response.setDepartment("IT");
        response.setJoiningDate(LocalDate.now());

        when(employeeService.updateEmployee(eq(1L), any(CreateEmployeeRequest.class)))
        .thenReturn(response);

        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Sneha Updated"))
                .andExpect(jsonPath("$.department").value("IT"));
    }

    @Test
    void deleteEmployeeTest() throws Exception {

        doNothing().when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isNoContent());
    }
}