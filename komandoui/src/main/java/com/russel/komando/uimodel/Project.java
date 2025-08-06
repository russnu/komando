package com.russel.komando.uimodel;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Project {
    private int projectId;
    private String projectName;
    private LocalDate projectStartDate;
    private LocalDate projectDueDate;
    private Status status;
    private String updatedAt;
    private String createdAt;
    private List<Employee> assignedEmployees;
}