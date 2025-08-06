package com.russel.komando.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.russel.komando.enums.Priority;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Task {

    private int taskId;
    private String taskName;
    private String description;
    private Priority priority;
    private LocalDate taskDueDate;
    private Project project;
    private Status status;
    private List<Employee> assignedEmployees;

    @JsonFormat(pattern = "MMMM dd, yyyy - hh:mm a", timezone = "GMT+08:00")
    private LocalDateTime updatedAt;

    @JsonFormat(pattern = "MMMM dd, yyyy - hh:mm a", timezone = "GMT+08:00")
    private LocalDateTime createdAt;
}
