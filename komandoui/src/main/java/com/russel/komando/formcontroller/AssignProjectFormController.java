package com.russel.komando.formcontroller;

import com.russel.komando.uimodel.Employee;
import com.russel.komando.uimodel.Project;
import com.russel.komando.uiservice.*;
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

public class AssignProjectFormController {
    @FXML
    private ListView<Project> projectListView;
    private final EmployeeService employeeService = new EmployeeService();
    private final ProjectService projectService = new ProjectService();
    private final ProjectAssignmentService projectAssignmentService = new ProjectAssignmentService();
    private final Map<Project, Boolean> projectSelectionMap = new HashMap<>();
    private Employee employee;
    private Stage parentStage;
    //================================================================================//
    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }
    //================================================================================//
    @FXML
    private void initialize() {

        projectListView.setCellFactory(CheckBoxListCell.forListView(
                project -> {
                    boolean preSelected = employee != null && project.getAssignedEmployees().contains(employee);
                    SimpleBooleanProperty selected = new SimpleBooleanProperty(preSelected);

                    projectSelectionMap.put(project, preSelected);

                    selected.addListener((obs, oldVal, newVal) -> projectSelectionMap.put(project, newVal));

                    return selected;
                },
                new StringConverter<Project>() {
                    @Override
                    public String toString(Project project) {
                        return project.getProjectName();
                    }

                    @Override
                    public Project fromString(String string) {
                        return null;
                    }
                }
        ));
    }
    //================================================================================//
    public void setEmployee(Employee employee) {
        this.employee = employee;
        List<Project> allProjects = projectService.findAll();
        for (Project project : allProjects) {
            List<Employee> assigned = employeeService.getEmployeesByProjectId(project.getProjectId());
            project.setAssignedEmployees(assigned);
        }
        projectListView.setItems(FXCollections.observableArrayList(allProjects));
        int rowHeight = 28;
        int maxHeight = 400;

        int listSize = allProjects.size();
        int computedHeight = listSize * rowHeight;
        projectListView.setPrefHeight(Math.min(computedHeight, maxHeight));
    }
    //================================================================================//
    @FXML
    private void handleSave() {
        try {
            List<Project> selectedProjects = projectSelectionMap.entrySet().stream()
                    .filter(Map.Entry::getValue)
                    .map(Map.Entry::getKey)
                    .toList();

            projectAssignmentService.removeAssignmentsByEmployeeId(employee.getEmployeeId());
            projectAssignmentService.assignEmployeeToProjects(employee, selectedProjects);

            String message;
            if (selectedProjects.size() == 1) {
                Project project = selectedProjects.get(0);
                message = employee.getFirstName() + " " +
                        employee.getLastName() +
                        " is assigned to project: " +
                        project.getProjectName();
            } else {
                message = employee.getFirstName() + " " +
                        employee.getLastName() +
                        " is assigned to " +
                        selectedProjects.size() + " projects.";
            }
            Toast.showSuccess(parentStage, message);
        } catch (Exception e) {
            Toast.showError(parentStage, "Failed assign employee to projects.");
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
        ((Stage) projectListView.getScene().getWindow()).close();
    }

}
