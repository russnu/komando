package com.russel.komando.uicontroller;

import com.russel.komando.formcontroller.AssignProjectFormController;
import com.russel.komando.formcontroller.AssignTaskFormController;
import com.russel.komando.formcontroller.TaskFormController;
import com.russel.komando.uimodel.Employee;
import com.russel.komando.uimodel.Project;
import com.russel.komando.uimodel.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeDetailsController {
    @FXML private Label employeeNameLabel;
    @FXML private Label employeeTitleLabel;
    @FXML private VBox taskTableInclude;
    @FXML private VBox projectTableInclude;
    private TaskTableController taskTableController;
    private ProjectTableController projectTableController;
    private Employee employee;
    //================================================================================//
    public void initialize() {
        loadEmployeeTasks();
        loadEmployeeProjects();
    }
    //================================================================================//
    public void setEmployeeData(Employee employee) {
        if (employee != null && taskTableController != null) {
            this.employee = employee;
            employeeNameLabel.setText(employee.getFirstName() + " " + employee.getLastName());
            employeeTitleLabel.setText(employee.getEmployeeTitle());
            taskTableController.setShowProjectColumn(true);
            taskTableController.setEmployeeId(employee.getEmployeeId());
            projectTableController.setEmployeeId(employee.getEmployeeId());
        }
    }
    //================================================================================//
    public void loadEmployeeTasks(){
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
    public void loadEmployeeProjects(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/russel/komando/fxml/tables/ProjectTable.fxml"));
            VBox table = loader.load();
            projectTableController = loader.getController();
            projectTableInclude.getChildren().setAll(table);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //================================================================================//
    @FXML
    private void handleAssignToExistingTask() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/russel/komando/fxml/forms/AssignTaskForm.fxml"));
            Parent root = loader.load();

            AssignTaskFormController controller = loader.getController();
            controller.setParentStage((Stage) employeeNameLabel.getScene().getWindow());
            controller.setEmployee(employee);

            Stage modal = new Stage();
            modal.setTitle("Assign Employee");
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
    private void handleAssignToExistingProject() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/russel/komando/fxml/forms/AssignProjectForm.fxml"));
            Parent root = loader.load();

            AssignProjectFormController controller = loader.getController();
            controller.setParentStage((Stage) employeeNameLabel.getScene().getWindow());
            controller.setEmployee(employee);

            Stage modal = new Stage();
            modal.setTitle("Assign Employee");
            modal.setScene(new javafx.scene.Scene(root));
            modal.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            modal.showAndWait();

            if (projectTableController != null) {
                projectTableController.refreshTable();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
