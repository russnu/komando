package com.russel.komando.util;

import com.russel.komando.entity.TaskData;
import com.russel.komando.model.Employee;
import com.russel.komando.model.Task;

import java.util.List;

public class TaskMapper {
    public static Task toModel(TaskData taskData, List<Employee> assignedEmployees, boolean includeProject) {
        if (taskData == null) return null;
        Task task = new Task();
        task.setTaskId(taskData.getTaskId());
        task.setTaskName(taskData.getTaskName());
        task.setDescription(taskData.getDescription());
        task.setPriority(taskData.getPriority());
        task.setTaskDueDate(taskData.getTaskDueDate());
        if (includeProject) {
            task.setProject(ProjectMapper.toModel(taskData.getProject()));
        }
        task.setStatus(StatusMapper.toModel(taskData.getStatus()));
        task.setAssignedEmployees(assignedEmployees);
        task.setCreatedAt(taskData.getCreatedAt());
        task.setUpdatedAt(taskData.getUpdatedAt());
        return task;
    }

    public static Task toModel(TaskData taskData) {
        return toModel(taskData, null, true);
    }

    public static Task toModel(TaskData taskData, List<Employee> assignedEmployees) {
        return toModel(taskData, assignedEmployees, true);
    }

    public static TaskData toEntity(Task task) {
        if (task == null) return null;
        TaskData taskData = new TaskData();
        taskData.setTaskId(task.getTaskId());
        taskData.setTaskName(task.getTaskName());
        taskData.setDescription(task.getDescription());
        taskData.setPriority(task.getPriority());
        taskData.setTaskDueDate(task.getTaskDueDate());
        taskData.setProject(ProjectMapper.toEntity(task.getProject()));
        taskData.setStatus(StatusMapper.toEntity(task.getStatus()));
        taskData.setCreatedAt(task.getCreatedAt());
        taskData.setUpdatedAt(task.getUpdatedAt());
        return taskData;
    }

}
