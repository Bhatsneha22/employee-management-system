package com.sneha.employee_management_backend.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeDTO {

    private Long employeeId;

    private String firstName;

    private String lastName;

    private String email;

    private String department;

    private LocalDate joiningDate;
}