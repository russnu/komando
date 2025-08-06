package com.russel.komando.formcontroller;

import com.russel.komando.uimodel.Employee;
import com.russel.komando.uimodel.Project;
import com.russel.komando.uimodel.Task;
import com.russel.komando.uiservice.EmployeeService;
import com.russel.komando.uiservice.ProjectAssignmentService;
import com.russel.komando.util.Toast;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MembersFormController {
    @FXML
    private ListView<Employee> employeeListView;
    private final EmployeeService employeeService = new EmployeeService();
    private final ProjectAssignmentService projectAssignmentService = new ProjectAssignmentService();
    private final Map<Employee, Boolean> employeeSelectionMap = new HashMap<>();
    private Project project;
    private Stage parentStage;
    //================================================================================//
    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }
    //================================================================================//
    @FXML
    private void initialize() {
        employeeListView.setCellFactory(CheckBoxListCell.forListView(
                employee -> {
                    boolean preSelected = project.getAssignedEmployees() != null &&
                            project.getAssignedEmployees().contains(employee);
                    SimpleBooleanProperty selected = new SimpleBooleanProperty(preSelected);

                    employeeSelectionMap.put(employee, preSelected);

                    selected.addListener((obs, oldVal, newVal) -> {
                        employeeSelectionMap.put(employee, newVal);
                    });

                    return selected;
                },
                new StringConverter<Employee>() {
                    @Override
                    public String toString(Employee employee) {
                        return employee.getFirstName() + " " + employee.getLastName() + " - " + employee.getEmployeeTitle();
                    }

                    @Override
                    public Employee fromString(String string) {
                        return null; // not needed for display-only
                    }
                }
        ));

    }
    //================================================================================//
    public void setProject(Project project) {
        this.project = project;

        List<Employee> employees = employeeService.findAll();
        employeeListView.setItems(FXCollections.observableArrayList(employees));
        int rowHeight = 28;
        int maxHeight = 400;

        int listSize = employees.size();
        int computedHeight = listSize * rowHeight;
        employeeListView.setPrefHeight(Math.min(computedHeight, maxHeight));
    }
    //================================================================================//
    @FXML
    private void handleSave() {
        try {
            List<Employee> selectedEmployees = employeeSelectionMap.entrySet().stream()
                    .filter(Map.Entry::getValue)
                    .map(Map.Entry::getKey)
                    .toList();

            projectAssignmentService.removeAssignmentsByProjectId(project.getProjectId());
            projectAssignmentService.assignEmployeesToProject(project, selectedEmployees);

            project.setAssignedEmployees(selectedEmployees);

            Toast.showSuccess(parentStage, "Project team updated successfully!");
        } catch (Exception e) {
            Toast.showError(parentStage, "Failed to update project team.");
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
        ((Stage) employeeListView.getScene().getWindow()).close();
    }
}
