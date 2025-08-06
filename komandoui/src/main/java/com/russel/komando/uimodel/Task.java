package com.russel.komando.uimodel;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Task {
    private int taskId;
    private String taskName;
    private String description;
    private String priority;
    private LocalDate taskDueDate;
    private Project project;
    private Status status;
    private String updatedAt;
    private String createdAt;
    private List<Employee> assignedEmployees;
}
