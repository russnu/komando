package com.russel.komando.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Status {
    private int statusId;
    private String statusTitle;

    @JsonFormat(pattern = "MMMM dd, yyyy - hh:mm a", timezone = "GMT+08:00")
    private LocalDateTime updatedAt;

    @JsonFormat(pattern = "MMMM dd, yyyy - hh:mm a", timezone = "GMT+08:00")
    private LocalDateTime createdAt;
}
