package com.russel.komando.uicontroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class NavigationController {
    @FXML
    private VBox contentArea;

    private static NavigationController instance;

    public NavigationController() {
        instance = this;
    }

    public static NavigationController getInstance() {
        return instance;
    }

    public void initialize() {
        showProjects(); // default
    }

    public void setContent(Node node) {
        contentArea.getChildren().setAll(node);
    }

    public void showProjects() {
        loadView("/com/russel/komando/fxml/ProjectPage.fxml");
    }

    public void showTasks() {
        loadView("/com/russel/komando/fxml/TaskPage.fxml");
    }

    public void showEmployees() {
        loadView("/com/russel/komando/fxml/EmployeePage.fxml");
    }

    private void loadView(String fxmlPath) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
