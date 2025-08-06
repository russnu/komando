package com.russel.komando.formcontroller;

import com.russel.komando.uimodel.Employee;
import com.russel.komando.uimodel.Project;
import com.russel.komando.uimodel.Status;
import com.russel.komando.uiservice.*;
import com.russel.komando.util.Toast;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectFormController {
    @FXML private Label titleText;
    @FXML private TextField projectNameField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker dueDatePicker;
    @FXML private ComboBox<Status> statusComboBox;
    @FXML private ListView<Employee> employeeListView;

    private final ProjectService projectService = new ProjectService();
    private final StatusService statusService = new StatusService();
    private final EmployeeService employeeService = new EmployeeService();

    private final ProjectAssignmentService projectAssignmentService = new ProjectAssignmentService();
    private Project project;
    private Stage parentStage;
    private boolean isCreateMode = false;
    private final Map<Employee, Boolean> employeeSelectionMap = new HashMap<>();
    //================================================================================//
    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }
    //================================================================================//
    public void setCreateMode(boolean isCreateMode) {
        this.isCreateMode = isCreateMode;
    }
    //================================================================================//
    @FXML
    private void initialize() {
        try {
            statusComboBox.setEditable(true);
            statusComboBox.getItems().clear();
            statusService.findAll().forEach(status ->
                    statusComboBox.getItems().add(status)
            );
        } catch (Exception e) {
            e.printStackTrace();
            Toast.showError(parentStage, "Failed to load statuses.");
        }
    }
    //================================================================================//
    public void setProject(Project project) {
        this.project = project;
        titleText.setText(isCreateMode ? "Create New Project" : "Edit Project");
        if (project != null) {
            projectNameField.setText(project.getProjectName());
            dueDatePicker.setValue(project.getProjectDueDate());
            startDatePicker.setValue(isCreateMode ? java.time.LocalDate.now(): project.getProjectStartDate());
            if (project.getStatus() != null) {
                statusComboBox.setValue(project.getStatus());
            }
            project.setAssignedEmployees(employeeService.getEmployeesByProjectId(project.getProjectId()));

            try {
                List<Employee> employees = employeeService.findAll();
                employeeListView.setItems(FXCollections.observableArrayList(employees));

                double cellHeight = 28;
                double maxHeight = 200;

                employeeListView.setFixedCellSize(cellHeight); // Required for calculation

                employeeListView.prefHeightProperty().bind(
                        Bindings.createDoubleBinding(() ->
                                        Math.min(employeeListView.getItems().size() * cellHeight, maxHeight),
                                employeeListView.getItems()
                        )
                );

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

            } catch (Exception e) {
//                Toast.showError(parentStage, "Failed to load employees.");
                e.printStackTrace();
            }
        }
    }
    //================================================================================//
    @FXML
    private void handleSave() {
        project.setProjectName(projectNameField.getText());
        project.setProjectStartDate(startDatePicker.getValue());
        project.setProjectDueDate(dueDatePicker.getValue());


        String inputTitle = statusComboBox.getEditor().getText().trim();

        try {
            Status existingStatus = statusService.findByTitle(inputTitle);

            if (existingStatus != null) {
                project.setStatus(existingStatus);
            } else {
                // Create and save new Status
                Status newStatus = new Status();
                newStatus.setStatusTitle(inputTitle);
                Status createdStatus = statusService.createStatus(newStatus);
                project.setStatus(createdStatus);
                statusComboBox.getItems().add(createdStatus); // Optional: update dropdown
            }
            List<Employee> selectedEmployees = employeeSelectionMap.entrySet().stream()
                    .filter(Map.Entry::getValue)
                    .map(Map.Entry::getKey)
                    .toList();

            if (isCreateMode) {
                project = projectService.createProject(project);
            } else {
                projectService.updateProject(project);
                projectAssignmentService.removeAssignmentsByProjectId(project.getProjectId());
            }
            projectAssignmentService.assignEmployeesToProject(project, selectedEmployees);

            project.setAssignedEmployees(selectedEmployees);

            Toast.showSuccess(parentStage, isCreateMode ? "Project created successfully!" : "Project updated successfully!");
        } catch (Exception e) {
            Toast.showError(parentStage, "Failed to save task or status.");
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
        ((Stage) titleText.getScene().getWindow()).close();
    }
}
