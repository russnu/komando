package com.russel.komando.formcontroller;

import com.russel.komando.uimodel.Employee;
import com.russel.komando.uimodel.Project;
import com.russel.komando.uimodel.Status;
import com.russel.komando.uimodel.Task;
import com.russel.komando.uiservice.EmployeeService;
import com.russel.komando.uiservice.StatusService;
import com.russel.komando.uiservice.TaskAssignmentService;
import com.russel.komando.uiservice.TaskService;
import com.russel.komando.util.Toast;
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

public class TaskFormController {
    @FXML private Label titleText;
    @FXML private TextField taskNameField;
    @FXML private TextArea descriptionField;
    @FXML private DatePicker dueDatePicker;
    @FXML private ComboBox<Status> statusComboBox;
    @FXML private ComboBox<String> priorityComboBox;
    @FXML private ListView<Employee> employeeListView;
    private final TaskService taskService = new TaskService();
    private final StatusService statusService = new StatusService();
    private final EmployeeService employeeService = new EmployeeService();
    private final TaskAssignmentService taskAssignmentService = new TaskAssignmentService();
    private Task task;
    private Stage parentStage;
    private Integer fallbackProjectId;
    private boolean isCreateMode = false;
    private final Map<Employee, Boolean> employeeSelectionMap = new HashMap<>();
    //================================================================================//
    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }
    //================================================================================//
    public void setFallbackProjectId(Integer fallbackProjectId) {
        this.fallbackProjectId = fallbackProjectId;
    }
    //================================================================================//
    public void setCreateMode(boolean isCreateMode) {
        this.isCreateMode = isCreateMode;
    }
    //================================================================================//
    @FXML
    private void initialize() {
        employeeListView.setPlaceholder(new Label("No employees assigned to project"));
        priorityComboBox.setItems(FXCollections.observableArrayList("LOW", "MEDIUM", "HIGH"));
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

        employeeListView.setCellFactory(CheckBoxListCell.forListView(
                employee -> {
                    boolean preSelected = task.getAssignedEmployees() != null &&
                            task.getAssignedEmployees().contains(employee);
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
    public void setTask(Task task) {
        this.task = task;
        titleText.setText(isCreateMode ? "Create New Task" : "Edit Task");
        if (task != null) {
            taskNameField.setText(task.getTaskName());
            descriptionField.setText(task.getDescription());
            dueDatePicker.setValue(task.getTaskDueDate());
            if (task.getStatus() != null) {
                statusComboBox.setValue(task.getStatus());
            }

            if (task.getPriority() != null) {
                priorityComboBox.setValue(task.getPriority());
            }

            try {
                Integer projectId = (task.getProject() != null)
                        ? task.getProject().getProjectId()
                        : fallbackProjectId;

                if (projectId != null) {
                    List<Employee> employees = employeeService.getEmployeesByProjectId(projectId);
                    employeeListView.setItems(FXCollections.observableArrayList(employees));
                }

                // Pre-select assigned employees
                if (task.getAssignedEmployees() != null) {
                    for (Employee emp : task.getAssignedEmployees()) {
                        if (employeeListView.getItems().contains(emp)) {
                            employeeListView.getSelectionModel().select(emp);
                        }
                    }
                }
            } catch (Exception e) {
//                Toast.showError(parentStage, "Failed to load employees.");
                e.printStackTrace();
            }
        }
    }
    //================================================================================//
    @FXML
    private void handleSave() {
        task.setTaskName(taskNameField.getText());
        task.setDescription(descriptionField.getText());
        task.setTaskDueDate(dueDatePicker.getValue());
        task.setPriority(priorityComboBox.getValue());

        String inputTitle = statusComboBox.getEditor().getText().trim();

        try {
            // Try to find existing status from backend

            if (task.getProject() == null && fallbackProjectId != null) {
                Project project = new Project();
                project.setProjectId(fallbackProjectId);
                task.setProject(project);
            }

            Status existingStatus = statusService.findByTitle(inputTitle);

            if (existingStatus != null) {
                task.setStatus(existingStatus);
            } else {
                // Create and save new Status
                Status newStatus = new Status();
                newStatus.setStatusTitle(inputTitle);
                Status createdStatus = statusService.createStatus(newStatus);
                task.setStatus(createdStatus);
                statusComboBox.getItems().add(createdStatus); // Optional: update dropdown
            }
//            List<Employee> selectedEmployees = employeeListView.getSelectionModel().getSelectedItems();
            List<Employee> selectedEmployees = employeeSelectionMap.entrySet().stream()
                    .filter(Map.Entry::getValue)
                    .map(Map.Entry::getKey)
                    .toList();

            if (isCreateMode) {
                task = taskService.createTask(task);
            } else {
                taskService.updateTask(task);
                taskAssignmentService.removeAssignmentsByTaskId(task.getTaskId());
            }

            taskAssignmentService.assignEmployeesToTask(task, selectedEmployees);

            task.setAssignedEmployees(selectedEmployees);

            Toast.showSuccess(parentStage, isCreateMode ? "Task created successfully!" : "Task updated successfully!");
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
        ((Stage) taskNameField.getScene().getWindow()).close();
    }
}
