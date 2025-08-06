package com.russel.komando.model;

import lombok.Data;

@Data
public class TaskAssignment {
    private int taId;
    private Task task;
    private Employee employee;
}
