package com.russel.komando.uicontroller;

import com.russel.komando.formcontroller.TaskFormController;
import com.russel.komando.uimodel.Task;
import com.russel.komando.uiservice.TaskService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class TaskController {
    @FXML private VBox taskTableInclude;
    private TaskTableController taskTableController;
    private TaskService taskService = new TaskService();
    //================================================================================//
    @FXML
    public void initialize() {
        loadTasks();
    }
    //================================================================================//
    public void loadTasks(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/russel/komando/fxml/tables/TaskTable.fxml"));
            VBox tableContent = loader.load();
            taskTableController = loader.getController();
            taskTableInclude.getChildren().setAll(tableContent);
            taskTableController.setShowProjectColumn(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
