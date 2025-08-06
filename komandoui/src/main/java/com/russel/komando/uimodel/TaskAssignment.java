package com.russel.komando.uimodel;

import lombok.Data;

@Data
public class TaskAssignment {
    private int taId;
    private Task task;
    private Employee employee;
}
