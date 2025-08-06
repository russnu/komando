package com.russel.komando.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Project {
    private int projectId;
    private String projectName;
    private LocalDate projectStartDate;
    private LocalDate projectDueDate;
    private Status status;

    @JsonFormat(pattern = "MMMM dd, yyyy - hh:mm a", timezone = "GMT+08:00")
    private LocalDateTime updatedAt;

    @JsonFormat(pattern = "MMMM dd, yyyy - hh:mm a", timezone = "GMT+08:00")
    private LocalDateTime createdAt;
}
