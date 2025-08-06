package com.russel.komando.base;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public abstract class BaseTableController<T> {
    @FXML
    protected TableView<T> tableView;
    protected abstract List<T> fetchData();

    @FXML
    public void initialize() {
        refreshTable();
    }

    public void refreshTable() {
        List<T> data = fetchData();
        ObservableList<T> observableData = FXCollections.observableArrayList(data);
        tableView.setItems(observableData);
        tableView.setFixedCellSize(32);
        tableView.prefHeightProperty().bind(
                Bindings.size(tableView.getItems())
                        .multiply(tableView.getFixedCellSize())
                        .add(32)
        );
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }


}
