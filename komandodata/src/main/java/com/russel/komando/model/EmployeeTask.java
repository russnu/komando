package com.russel.komando.model;

import com.russel.komando.enums.Priority;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeTask {
    private Integer taskId;
    private String taskName;
    private String description;
    private Status status;
    private Priority priority;
    private LocalDate taskDueDate;
    private Project project;
}
