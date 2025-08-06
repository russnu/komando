package com.russel.komando.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ProjectAssignment")
public class ProjectAssignmentData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pa_id")
    private int paId;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectData project;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeData employee;
}
