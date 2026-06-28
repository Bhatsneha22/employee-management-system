package com.sneha.employee_management_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

   @NotBlank(message = "First name is required")
@Column(nullable = false)
private String firstName;

@NotBlank(message = "Last name is required")
@Column(nullable = false)
private String lastName;

@NotBlank(message = "Email is required")
@Email(message = "Please enter a valid email")
@Column(unique = true, nullable = false)
private String email;

@NotBlank(message = "Phone number is required")
private String phoneNumber;

@NotBlank(message = "Department is required")
private String department;

@NotNull(message = "Salary is required")
@Positive(message = "Salary must be greater than 0")
private Double salary;

@NotNull(message = "Joining date is required")
private LocalDate joiningDate;
}