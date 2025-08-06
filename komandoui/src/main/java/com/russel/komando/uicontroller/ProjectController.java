package com.russel.komando.uicontroller;

import com.russel.komando.formcontroller.ProjectFormController;
import com.russel.komando.formcontroller.TaskFormController;
import com.russel.komando.uimodel.Project;
import com.russel.komando.uimodel.Task;
import com.russel.komando.uiservice.ProjectService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ProjectController {
    @FXML private VBox projectTableInclude;
    @FXML private Button createProjectButton;

    private ProjectTableController projectTableController;
    private ProjectService projectService = new ProjectService();
    //================================================================================//
    @FXML
    public void initialize() {
        loadProjects();
    }
    //================================================================================//
    public void loadProjects(){
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
    public void handleCreateProject(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/russel/komando/fxml/forms/ProjectForm.fxml"));
            Parent root = loader.load();

            ProjectFormController controller = loader.getController();
            controller.setCreateMode(true);
            controller.setParentStage((Stage) createProjectButton.getScene().getWindow());

            Project newProject = new Project();

            controller.setProject(newProject);

            Stage modal = new Stage();
            modal.setTitle("Create New Project");
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
    //================================================================================//
}
