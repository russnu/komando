package com.russel.komando.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeProject {
    private Integer projectId;
    private String projectName;
    private LocalDate projectStartDate;
    private LocalDate projectDueDate;
    private Status status;
}
