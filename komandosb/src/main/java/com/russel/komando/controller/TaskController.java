package com.russel.komando.controller;

import com.russel.komando.model.Task;
import com.russel.komando.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/tasks")
@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;
    //========================================================================================================//
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.fetchAllTasks();
    }
    //========================================================================================================//
    @GetMapping("/{id}")
    public Task getTask(@PathVariable Integer id) {
        return taskService.fetchTask(id);
    }
    //========================================================================================================//
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task) {
        return taskService.create(task);
    }
    //========================================================================================================//
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Integer id, @RequestBody Task task) {
        task.setTaskId(id);
        return taskService.update(task);
    }
    //========================================================================================================//
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Integer id) {
        taskService.delete(id);
    }
    //========================================================================================================//
}
