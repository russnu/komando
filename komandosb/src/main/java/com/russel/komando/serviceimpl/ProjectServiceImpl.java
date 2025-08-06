package com.russel.komando.serviceimpl;

import com.russel.komando.entity.ProjectAssignmentData;
import com.russel.komando.entity.ProjectData;
import com.russel.komando.entity.StatusData;
import com.russel.komando.entity.TaskData;
import com.russel.komando.exception.NotFound;
import com.russel.komando.model.Employee;
import com.russel.komando.model.Project;
import com.russel.komando.model.Task;
import com.russel.komando.repository.ProjectAssignmentRepository;
import com.russel.komando.repository.ProjectRepository;
import com.russel.komando.repository.StatusRepository;
import com.russel.komando.service.ProjectService;
import com.russel.komando.service.TaskService;
import com.russel.komando.util.EmployeeMapper;
import com.russel.komando.util.ProjectMapper;
import com.russel.komando.util.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProjectAssignmentRepository projectAssignmentRepository;

    //========================================================================================================//
    @Override
    public List<Project> fetchAllProjects() {
        List<ProjectData> projectDataList = new ArrayList<>();
        projectRepository.findAll().forEach(projectDataList::add);

        List<Project> projects = new ArrayList<>();
        for (ProjectData projectData : projectDataList){
            Project project = ProjectMapper.toModel(projectData);
            projects.add(project);
        }
        return projects;
    }
    //========================================================================================================//
    @Override
    public Project fetchProject(Integer projectId){
        ProjectData projectData = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFound("Project with ID " + projectId + " not found."));
        if (projectData == null) return null;

        Project project = ProjectMapper.toModel(projectData);

        return project;
    }
    //========================================================================================================//
    @Override
    public List<Task> fetchProjectTasks(Integer projectId){
        ProjectData projectData = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFound("Project with ID " + projectId + " not found."));
        List<Task> tasks = new ArrayList<>();
        if (projectData.getTasks() != null) {
            for (TaskData taskData : projectData.getTasks()) {
                List<Employee> assignedEmployees = taskService.fetchTaskEmployees(taskData.getTaskId());
                Task task = TaskMapper.toModel(taskData, assignedEmployees, false);
                tasks.add(task);
            }
        }
        return tasks;
    }
    //========================================================================================================//
    @Override
    public List<Employee> fetchProjectEmployees(Integer employeeId) {
        List<ProjectAssignmentData> assignmentDataList = projectAssignmentRepository.findByProject_ProjectId(employeeId);
        List<Employee> employees = new ArrayList<>();

        for(ProjectAssignmentData assignment : assignmentDataList){
            Employee employee = EmployeeMapper.toModel(assignment.getEmployee());
            employees.add(employee);
        }

        return employees;
    }
    //========================================================================================================//
    @Override
    public Project create(Project project){

        ProjectData projectData = ProjectMapper.toEntity(project);
        StatusData statusData = statusRepository.findByStatusTitleIgnoreCase("In Progress")
                .orElseThrow(() -> new NotFound("Default status 'In Progress' not found"));
        projectData.setStatus(statusData);

        ProjectData saved = projectRepository.save(projectData);
        return ProjectMapper.toModel(saved);
    }
    //========================================================================================================//
    @Override
    public Project update(Project project){
        ProjectData existing = projectRepository.findById(project.getProjectId())
                .orElseThrow(() -> new NotFound("Project with ID " + project.getProjectId() + " not found."));

        existing.setProjectName(project.getProjectName());
        existing.setProjectStartDate(project.getProjectStartDate());
        existing.setProjectDueDate(project.getProjectDueDate());
        existing.setStatus(statusRepository.findById(project.getStatus().getStatusId())
                .orElseThrow(() -> new NotFound("Status with ID " + project.getStatus().getStatusId() + " not found.")));

        ProjectData saved = projectRepository.save(existing);
        return ProjectMapper.toModel(saved);
    }
    //========================================================================================================//
    @Override
    public void delete(Integer projectId) {
        projectRepository.deleteById(projectId);
    }
    //========================================================================================================//

}
