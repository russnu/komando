package com.russel.komando.service;

import com.russel.komando.model.Employee;
import com.russel.komando.model.Task;
import java.util.List;

public interface TaskService {
    List<Task> fetchAllTasks();
    Task fetchTask(Integer taskId);
    Task create(Task task);
    Task update(Task task);
    void delete(Integer taskId);
    List<Employee> fetchTaskEmployees(int taskId);
}
