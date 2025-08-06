package com.russel.komando.uicontroller;

import com.russel.komando.base.BaseTableController;
import com.russel.komando.formcontroller.ProjectFormController;
import com.russel.komando.formcontroller.TaskFormController;
import com.russel.komando.uimodel.Project;
import com.russel.komando.uimodel.Task;
import com.russel.komando.uiservice.ProjectService;
import com.russel.komando.util.ConfirmDialog;
import com.russel.komando.util.DateUtils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ProjectTableController extends BaseTableController {
    private Integer employeeId = null;
    @FXML private TableView<Project> tableView;
//    @FXML private TableColumn<Project, Long> idColumn;
    @FXML private TableColumn<Project, String> nameColumn;
    @FXML private TableColumn<Project, String> statusColumn;
    @FXML private TableColumn<Project, LocalDate> startDateColumn;
    @FXML private TableColumn<Project, LocalDate> endDateColumn;
    private final ProjectService projectService = new ProjectService();
    //================================================================================//
    @FXML
    public void initialize() {
        setupTable();
        refreshTable();

        tableView.setRowFactory(tv -> {
            TableRow<Project> row = new TableRow<>();

            // Create ContextMenu
            ContextMenu contextMenu = new ContextMenu();
            MenuItem editItem = new MenuItem("Edit Project");
            editItem.getStyleClass().add("first-menu-item");
            MenuItem deleteItem = new MenuItem("Delete");
            deleteItem.getStyleClass().add("last-menu-item");

            editItem.setOnAction(e -> {
                Project project = row.getItem();
                if (project != null) {
                    handleEdit(project);
                }
            });

            deleteItem.setOnAction(e -> {
                Project project = row.getItem();
                if (project != null) {
                    handleDelete(project);
                }
            });

            contextMenu.getItems().addAll(editItem, deleteItem);

            row.setOnContextMenuRequested(event -> {
                if (!row.isEmpty()) {
                    contextMenu.show(row, event.getScreenX(), event.getScreenY());
                } else {
                    contextMenu.hide();
                }
            });

            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    Project clickedProject = row.getItem();
                    showProjectDetails(clickedProject.getProjectId());
                }
            });
            return row;
        });
    }
    //================================================================================//
    @Override
    protected List<Project> fetchData() {
        if (employeeId != null) {
            return projectService.getProjectsByEmployeeId(employeeId);
        }
        return projectService.findAll();
    }
    //================================================================================//
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
        refreshTable();
    }
    //================================================================================//
    protected void setupTable() {
//        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleLongProperty(cellData.getValue().getProjectId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProjectName()));
        statusColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatus().getStatusTitle()));
        startDateColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getProjectStartDate()));
        startDateColumn.setCellFactory(column -> new TableCell<>() {
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
        endDateColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getProjectDueDate()));
        endDateColumn.setCellFactory(column -> new TableCell<>() {
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
    }

    //================================================================================//
    private void showProjectDetails(Integer projectId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/russel/komando/fxml/ProjectDetails.fxml"));
            Parent detailRoot = loader.load();

            ProjectDetailsController controller = loader.getController();
            Project project = projectService.getById(projectId);
            controller.setProjectData(project);

            NavigationController.getInstance().setContent(detailRoot);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    //================================================================================//
    private void handleEdit(Project project) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/russel/komando/fxml/forms/ProjectForm.fxml"));
            Parent root = loader.load();

            ProjectFormController controller = loader.getController();

            controller.setParentStage((Stage) tableView.getScene().getWindow());
            controller.setProject(project);

            Stage modal = new Stage();
            modal.setTitle("Edit Project");
            modal.setScene(new Scene(root));
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.showAndWait();

            refreshTable();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //================================================================================//
    private void handleDelete(Project project) {
        ConfirmDialog.showDeleteConfirmation("project", project.getProjectName())
                .ifPresent(response -> {
                    if (response.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                        projectService.deleteById(project.getProjectId());
                        System.out.println("Deleted: " + project.getProjectName());
                        refreshTable();
                    } else {
                        System.out.println("Deletion cancelled.");
                    }
                });
    }
}