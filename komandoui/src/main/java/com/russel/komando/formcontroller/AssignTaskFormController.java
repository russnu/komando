package com.russel.komando.formcontroller;

import com.russel.komando.uimodel.Employee;
import com.russel.komando.uimodel.Project;
import com.russel.komando.uimodel.Task;
import com.russel.komando.uimodel.TaskAssignment;
import com.russel.komando.uiservice.EmployeeService;
import com.russel.komando.uiservice.ProjectService;
import com.russel.komando.uiservice.TaskAssignmentService;
import com.russel.komando.uiservice.TaskService;
import com.russel.komando.util.Toast;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssignTaskFormController {
    @FXML
    private ListView<Task> taskListView;
    private final EmployeeService employeeService = new EmployeeService();
    private final ProjectService projectService = new ProjectService();
    private final TaskService taskService = new TaskService();
    private final TaskAssignmentService taskAssignmentService = new TaskAssignmentService();
    private final Map<Task, Boolean> taskSelectionMap = new HashMap<>();
    private Employee employee;
    private Stage parentStage;
    //================================================================================//
    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }
    //================================================================================//
    @FXML
    private void initialize() {
        taskListView.setCellFactory(CheckBoxListCell.forListView(
                task -> {
                    boolean preSelected = task.getAssignedEmployees().contains(employee);
                    SimpleBooleanProperty selected = new SimpleBooleanProperty(preSelected);

                    taskSelectionMap.put(task, preSelected);

                    selected.addListener((obs, oldVal, newVal) -> taskSelectionMap.put(task, newVal));

                    return selected;
                },
                new StringConverter<Task>() {
                    @Override
                    public String toString(Task task) {
                        String projectName = task.getProject() != null ? task.getProject().getProjectName() : "No Project";
                        return task.getTaskName() + " (" + projectName + ")";
                    }

                    @Override
                    public Task fromString(String string) {
                        return null;
                    }
                }
        ));
    }
    //================================================================================//
    public void setEmployee(Employee employee) {
        this.employee = employee;
        loadTasksAssignedToEmployeeProjects();

    }
    //================================================================================//
    private void loadTasksAssignedToEmployeeProjects() {
        // 1. Get all projects assigned to the employee
        List<Project> projects = projectService.getProjectsByEmployeeId(employee.getEmployeeId());

        // 2. Collect all tasks from those projects
        List<Task> allTasks = projects.stream()
                .flatMap(project -> taskService.getTasksByProjectId(project.getProjectId())
                        .stream().peek(task -> task.setProject(project)))
                .toList();

        // 3. Populate the list view
        taskListView.setItems(FXCollections.observableArrayList(allTasks));

        int rowHeight = 28;
        int maxHeight = 400;

        int listSize = allTasks.size();
        int computedHeight = listSize * rowHeight;
        taskListView.setPrefHeight(Math.min(computedHeight, maxHeight));
    }
    //================================================================================//
    @FXML
    private void handleSave() {
        try {
            List<Task> selectedTasks = taskSelectionMap.entrySet().stream()
                    .filter(Map.Entry::getValue)
                    .map(Map.Entry::getKey)
                    .toList();

            taskAssignmentService.removeAssignmentsByEmployeeId(employee.getEmployeeId());
            taskAssignmentService.assignEmployeeToTasks(employee, selectedTasks);

            String message;
            if (selectedTasks.size() == 1) {
                Task task = selectedTasks.get(0);
                message = employee.getFirstName() + " " +
                        employee.getLastName() +
                        " is assigned to task: " +
                        task.getTaskName();
            } else {
                message = employee.getFirstName() + " " +
                        employee.getLastName() +
                        " is assigned to " +
                        selectedTasks.size() + " tasks.";
            }

            Toast.showSuccess(parentStage, message);
        } catch (Exception e) {
            Toast.showError(parentStage, "Failed assign employee to tasks.");
            e.printStackTrace();
        }
        closeWindow();
    }
    //================================================================================//
    @FXML
    private void handleCancel() {
        closeWindow();
    }
    //================================================================================//
    private void closeWindow() {
        ((Stage) taskListView.getScene().getWindow()).close();
    }
}
