package com.russel.komando.uicontroller;

import com.russel.komando.base.BaseTableController;
import com.russel.komando.uimodel.Employee;
import com.russel.komando.uiservice.EmployeeService;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.List;

public class EmployeeTableController extends BaseTableController<Employee> {
    private Integer projectId = null;
    @FXML private TableView<Employee> tableView;
//    @FXML private TableColumn<Employee, Long> idColumn;
    @FXML private TableColumn<Employee, String> fullNameColumn;
    @FXML private TableColumn<Employee, String> employeeTitleColumn;
    private final EmployeeService employeeService = new EmployeeService();
    //================================================================================//
    @FXML
    public void initialize() {
        setupTable();
        refreshTable();

        tableView.setRowFactory(tv -> {
            TableRow<Employee> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    Employee clickedEmployee = row.getItem();
                    showEmployeeDetails(clickedEmployee.getEmployeeId());
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
    @Override
    protected List<Employee> fetchData() {
        if (projectId != null) {
            return employeeService.getEmployeesByProjectId(projectId);
        }
        return employeeService.findAll();
    }
    //================================================================================//
    protected void setupTable() {
//        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleLongProperty(cellData.getValue().getEmployeeId()).asObject());
        fullNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName() + " " + cellData.getValue().getLastName()));
        employeeTitleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployeeTitle()));
    }

    private void showEmployeeDetails(Integer employeeId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/russel/komando/fxml/EmployeeDetails.fxml"));
            Parent detailRoot = loader.load();

            EmployeeDetailsController controller = loader.getController();
            Employee employee = employeeService.getById(employeeId);
            controller.setEmployeeData(employee);

            NavigationController.getInstance().setContent(detailRoot);
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
