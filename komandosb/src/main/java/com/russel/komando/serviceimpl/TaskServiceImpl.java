package com.russel.komando.serviceimpl;

import com.russel.komando.entity.ProjectData;
import com.russel.komando.entity.StatusData;
import com.russel.komando.entity.TaskAssignmentData;
import com.russel.komando.entity.TaskData;
import com.russel.komando.exception.NotFound;
import com.russel.komando.model.Employee;
import com.russel.komando.model.Task;
import com.russel.komando.repository.ProjectRepository;
import com.russel.komando.repository.StatusRepository;
import com.russel.komando.repository.TaskAssignmentRepository;
import com.russel.komando.repository.TaskRepository;
import com.russel.komando.service.TaskService;
import com.russel.komando.util.EmployeeMapper;
import com.russel.komando.util.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TaskAssignmentRepository taskAssignmentRepository;
    //========================================================================================================//
    @Override
    public List<Task> fetchAllTasks(){
        List<TaskData> taskDataList = new ArrayList<>();
        taskRepository.findAll().forEach(taskDataList::add);

        List<Task> tasks = new ArrayList<>();
        for (TaskData data : taskDataList){
            List<Employee> employees = fetchTaskEmployees(data.getTaskId());
            Task task = TaskMapper.toModel(data, employees);
            tasks.add(task);
        }
        return tasks;
    };
    //========================================================================================================//
    @Override
    public Task fetchTask(Integer taskId) {
        TaskData data = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFound("Task with ID " + taskId + " not found."));
        if (data == null) return null;

        List<Employee> employees = fetchTaskEmployees(data.getTaskId());
        Task task = TaskMapper.toModel(data, employees);

        return task;
    }
    //========================================================================================================//
    @Override
    public Task create(Task task) {

        TaskData taskData = TaskMapper.toEntity(task);

        ProjectData projectData = projectRepository.findById(task.getProject().getProjectId())
                .orElseThrow(() -> new NotFound("Project with ID " + task.getProject().getProjectId() + " not found."));
        taskData.setProject(projectData);

        StatusData statusData = statusRepository.findByStatusTitleIgnoreCase("In Progress")
                .orElseThrow(() -> new NotFound("Default status 'In Progress' not found"));
        taskData.setStatus(statusData);

        TaskData saved = taskRepository.save(taskData);

        return TaskMapper.toModel(saved);
    }
    //========================================================================================================//
    @Override
    public Task update(Task task) {
        TaskData existing = taskRepository.findById(task.getTaskId())
                .orElseThrow(() -> new NotFound("Task with ID " + task.getTaskId() + " not found."));

        existing.setTaskName(task.getTaskName());
        existing.setDescription(task.getDescription());
        existing.setPriority(task.getPriority());
        existing.setTaskDueDate(task.getTaskDueDate());

        existing.setProject(projectRepository.findById(task.getProject().getProjectId())
                .orElseThrow(() -> new NotFound("Project with ID " + task.getProject().getProjectId() + " not found.")));
        existing.setStatus(statusRepository.findById(task.getStatus().getStatusId())
                .orElseThrow(() -> new NotFound("Status with ID " + task.getStatus().getStatusId() + " not found.")));

        TaskData saved = taskRepository.save(existing);
        return TaskMapper.toModel(saved);
    }
    //========================================================================================================//
    @Override
    public void delete(Integer taskId) {
        taskRepository.deleteById(taskId);
    }
    //========================================================================================================//

    @Override
    public List<Employee> fetchTaskEmployees(int taskId) {
        List<TaskAssignmentData> assignmentDataList = taskAssignmentRepository.findByTask_TaskId(taskId);
        List<Employee> employees = new ArrayList<>();

        for (TaskAssignmentData assignment : assignmentDataList) {
            Employee employee = EmployeeMapper.toModel(assignment.getEmployee());
            employees.add(employee);
        }

        return employees;
    }
}
