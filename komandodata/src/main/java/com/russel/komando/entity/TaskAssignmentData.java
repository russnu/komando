package com.russel.komando.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TaskAssignment")
public class TaskAssignmentData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ta_id")
    private int taId;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private TaskData task;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeData employee;
}
