package com.russel.komando.uicontroller;

import com.russel.komando.formcontroller.MembersFormController;
import com.russel.komando.formcontroller.TaskFormController;
import com.russel.komando.uimodel.Employee;
import com.russel.komando.uimodel.Project;
import com.russel.komando.uimodel.Task;
import com.russel.komando.uiservice.EmployeeService;
import com.russel.komando.uiservice.ProjectAssignmentService;
import com.russel.komando.util.DateUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ProjectDetailsController {
    @FXML private Label projectNameLabel;
    @FXML private TextFlow dueDateTextFlow;
    @FXML private TextFlow statusTextFlow;
    @FXML private VBox taskTableInclude;
    @FXML private VBox employeeTableInclude;
    @FXML private Button createTaskButton;
    @FXML private Button addMembersButton;
    private Project currentProject;
    private TaskTableController taskTableController;
    private EmployeeTableController employeeTableController;
    private final EmployeeService employeeService = new EmployeeService();
    //================================================================================//
    public void initialize() {
        loadProjectTasks();
        loadProjectMembers();
    }
    //================================================================================//
    public void setProjectData(Project project) {
        if (project != null) {
            this.currentProject = project;

            List<Employee> assignedEmployees = employeeService.getEmployeesByProjectId(project.getProjectId());
            this.currentProject.setAssignedEmployees(assignedEmployees);

            projectNameLabel.setText(project.getProjectName());

            Text statusLabelText = new Text("Status: ");
            statusLabelText.setFont(Font.font("Poppins", FontWeight.BOLD, 14));
            Text statusText = new Text(project.getStatus().getStatusTitle());
            statusText.setFont(Font.font("Poppins", FontPosture.ITALIC, 14));
            statusTextFlow.getChildren().setAll(statusLabelText, statusText);
            //---------------------------------------------------------------------------//
            Text dateLabelText = new Text("Due Date: ");
            dateLabelText.setFont(Font.font("Poppins", FontWeight.BOLD, 14));
            Text dateText = new Text(project.getProjectDueDate().format(DateUtils.dateDisplayFormatter));
            dateText.setFont(Font.font("Poppins", FontPosture.ITALIC, 14));
            dueDateTextFlow.getChildren().setAll(dateLabelText, dateText);

            if (taskTableController != null) {
                taskTableController.setShowProjectColumn(false);
                taskTableController.setProjectId(project.getProjectId());
            }

            if (employeeTableController != null) {
                employeeTableController.setProjectId(project.getProjectId());
            }
        }
    }
    //================================================================================//
    public void loadProjectTasks(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/russel/komando/fxml/tables/TaskTable.fxml"));
            VBox table = loader.load();
            taskTableController = loader.getController();
            taskTableInclude.getChildren().setAll(table);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //================================================================================//
    public void loadProjectMembers(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/russel/komando/fxml/tables/EmployeeTable.fxml"));
            VBox table = loader.load();
            employeeTableController = loader.getController();
            employeeTableInclude.getChildren().setAll(table);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //================================================================================//
    @FXML
    public void handleCreate(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/russel/komando/fxml/forms/TaskForm.fxml"));
            Parent root = loader.load();

            TaskFormController controller = loader.getController();
            controller.setCreateMode(true); // enable create mode
            controller.setParentStage((Stage) createTaskButton.getScene().getWindow());

            Task newTask = new Task();

            if (currentProject != null) {
                controller.setFallbackProjectId(currentProject.getProjectId());
            }

            controller.setTask(newTask);

            Stage modal = new Stage();
            modal.setTitle("Create New Task");
            modal.setScene(new javafx.scene.Scene(root));
            modal.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            modal.showAndWait();

            if (taskTableController != null) {
                taskTableController.refreshTable();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //================================================================================//
    @FXML
    public void handleAddMembers(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/russel/komando/fxml/forms/MembersForm.fxml"));
            Parent root = loader.load();

            MembersFormController controller = loader.getController();
            controller.setParentStage((Stage) addMembersButton.getScene().getWindow());

            controller.setProject(currentProject);

            Stage modal = new Stage();
            modal.setTitle("Manage Project Team");
            modal.setScene(new javafx.scene.Scene(root));
            modal.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            modal.showAndWait();

            if (employeeTableController != null) {
                employeeTableController.refreshTable();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }


    }


}
