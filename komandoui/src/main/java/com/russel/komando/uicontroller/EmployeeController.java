package com.russel.komando.uicontroller;

import com.russel.komando.uiservice.EmployeeService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class EmployeeController {
    @FXML
    private VBox employeeTableInclude;
    private EmployeeTableController employeeTableController;
    private EmployeeService employeeService = new EmployeeService();
    //================================================================================//
    @FXML
    public void initialize() {
        loadEmployees();
    }
    //================================================================================//
    public void loadEmployees(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/russel/komando/fxml/tables/EmployeeTable.fxml"));
            VBox tableContent = loader.load();
            employeeTableController = loader.getController();
            employeeTableInclude.getChildren().setAll(tableContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
