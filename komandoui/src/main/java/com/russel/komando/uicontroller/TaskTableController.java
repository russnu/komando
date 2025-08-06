package com.russel.komando.uicontroller;

import com.russel.komando.base.BaseTableController;
import com.russel.komando.formcontroller.TaskFormController;
import com.russel.komando.uimodel.Task;
import com.russel.komando.uimodel.Employee;
import com.russel.komando.uiservice.TaskService;
import com.russel.komando.util.ConfirmDialog;
import com.russel.komando.util.DateUtils;
import com.russel.komando.util.PriorityUtils;
import com.russel.komando.util.StringUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Data;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static com.russel.komando.util.PriorityUtils.getPriorityWeight;

public class TaskTableController extends BaseTableController<Task> {

    private Integer projectId = null;
    private Integer employeeId = null;
//    @FXML private TableColumn<Task, Long> idColumn;
    @FXML private TableColumn<Task, String> titleColumn;
    @FXML private TableColumn<Task, String> descColumn;
    @FXML private TableColumn<Task, String> statusColumn;
    @FXML private TableColumn<Task, String> priorityColumn;
    @FXML private TableColumn<Task, LocalDate> dueColumn;
    @FXML private TableColumn<Task, String> projectColumn;
    @FXML private TableColumn<Task, String> assignedEmployeeColumn;
    private final TaskService taskService = new TaskService();
    //================================================================================//
    @FXML
    public void initialize() {
        setupTable();
        refreshTable();

        tableView.setRowFactory(tv -> {
            TableRow<Task> row = new TableRow<>();

            // Create ContextMenu
            ContextMenu contextMenu = new ContextMenu();
            MenuItem editItem = new MenuItem("Edit Task");
            editItem.getStyleClass().add("first-menu-item");
            MenuItem deleteItem = new MenuItem("Delete");
            deleteItem.getStyleClass().add("last-menu-item");

            editItem.setOnAction(e -> {
                Task task = row.getItem();
                if (task != null) {
                    handleEdit(task);
                }
            });

            deleteItem.setOnAction(e -> {
                Task task = row.getItem();
                if (task != null) {
                    handleDelete(task);
                }
            });

            contextMenu.getItems().addAll(editItem, deleteItem);

            // Show context menu only if the row is not empty
            row.setOnContextMenuRequested(event -> {
                if (!row.isEmpty()) {
                    contextMenu.show(row, event.getScreenX(), event.getScreenY());
                } else {
                    contextMenu.hide();
                }
            });

            return row;
        });
    }
    //================================================================================//
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
        refreshTable();
    }
    //================================================================================//
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
        refreshTable();
    }
    //================================================================================//
    @Override
    protected List<Task> fetchData() {
        if (projectId != null) {
            return taskService.getTasksByProjectId(projectId);
        } else if (employeeId != null){
            return taskService.getTasksByEmployeeId(employeeId);
        }
        return taskService.findAll();
    }
    //================================================================================//
    public void setShowProjectColumn(boolean visible) {
        if (projectColumn != null) {
            projectColumn.setVisible(visible);
//            projectColumn.setManaged(visible); // ensures layout adjusts
        }
    }
    //================================================================================//
    protected void setupTable() {
//        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleLongProperty(cellData.getValue().getTaskId()).asObject());
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTaskName()));
        descColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        priorityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPriority()));
        priorityColumn.setComparator(Comparator.comparingInt(PriorityUtils::getPriorityWeight));
        priorityColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String priority, boolean empty) {
                super.updateItem(priority, empty);
                if (empty || priority == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }

                Label badge = new Label(priority);
                badge.getStyleClass().addAll("badge");
                switch (priority) {
                    case "HIGH" -> badge.getStyleClass().add("priority-high");
                    case "MEDIUM" -> badge.getStyleClass().add("priority-medium");
                    case "LOW" -> badge.getStyleClass().add("priority-low");
                    default -> {}
                }

                setGraphic(badge);
                setText(null);
            }
        });


        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().getStatusTitle()));
        dueColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getTaskDueDate())
        );
        dueColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || date == null) {
                    setText(null);
                } else {
                    setText(date.format(DateUtils.dateDisplayFormatter));
                }
            }
        });
        projectColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProject().getProjectName()));
        assignedEmployeeColumn.setCellValueFactory(cellData -> {
            List<Employee> employees = cellData.getValue().getAssignedEmployees();
            if (employees == null || employees.isEmpty()) {
                return new SimpleStringProperty("-");
            }

            String fullNames = employees.stream()
                    .map(emp -> emp.getFirstName() + " " + emp.getLastName())
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("-");

            return new SimpleStringProperty(fullNames);
        });
    }
    //================================================================================//
    private void handleEdit(Task task) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/russel/komando/fxml/forms/TaskForm.fxml"));
            Parent root = loader.load();

            TaskFormController controller = loader.getController();

            controller.setParentStage((Stage) tableView.getScene().getWindow());

            if (task.getProject() == null && projectId != null) {
                controller.setFallbackProjectId(projectId);
            }
            controller.setTask(task); // populate data
            Stage modal = new Stage();
            modal.setTitle("Edit Task");
            modal.setScene(new Scene(root));
            modal.initModality(Modality.APPLICATION_MODAL); // this blocks until window is closed
            modal.showAndWait(); // open modal

            // Optional: refresh table after edit
            refreshTable();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //================================================================================//
    private void handleDelete(Task task) {
        ConfirmDialog.showDeleteConfirmation("task", task.getTaskName())
            .ifPresent(response -> {
                if (response.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    taskService.deleteById(task.getTaskId());
                    refreshTable();
                    System.out.println("Deleted: " + task.getTaskName());
                } else {
                    System.out.println("Deletion cancelled.");
                }
            });
    }
}
