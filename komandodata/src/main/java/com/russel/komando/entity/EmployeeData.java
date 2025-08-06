package com.russel.komando.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Employee")
public class EmployeeData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private int employeeId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "employee_title")
    private String employeeTitle;


    // Relationships ======================================= //

    @OneToMany(mappedBy = "employee")
    private List<TaskAssignmentData> taskAssignments;

    @OneToMany(mappedBy = "employee")
    private List<ProjectAssignmentData> projectAssignments;

}
