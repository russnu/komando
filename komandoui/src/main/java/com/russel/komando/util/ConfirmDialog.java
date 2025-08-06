package com.russel.komando.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Optional;

public class ConfirmDialog {
    public static Optional<ButtonType> showDeleteConfirmation(String objectType, String itemName) {
        ButtonType deleteButton = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION, "", deleteButton, cancelButton);

        confirmDialog.setTitle("Confirm Deletion");
        String capitalizedType = objectType.substring(0, 1).toUpperCase() + objectType.substring(1);
        confirmDialog.setHeaderText("Are you sure you want to delete this " + objectType + "?");
        confirmDialog.setContentText(capitalizedType + " name : " +  itemName);

        ImageView deleteIcon = new ImageView(new Image(
                ConfirmDialog.class.getResource("/com/russel/komando/icons/delete-icon.png").toExternalForm()
        ));
        deleteIcon.setFitHeight(48);
        deleteIcon.setFitWidth(48);
        confirmDialog.setGraphic(deleteIcon);

        Button deleteFxButton = (Button) confirmDialog.getDialogPane().lookupButton(deleteButton);
        Button cancelFxButton = (Button) confirmDialog.getDialogPane().lookupButton(cancelButton);

        deleteFxButton.getStyleClass().add("delete-button");
        cancelFxButton.getStyleClass().add("cancel-button");

        confirmDialog.getDialogPane().getStylesheets().add(
                ConfirmDialog.class.getResource("/com/russel/komando/css/confirm.css").toExternalForm()
        );
        return confirmDialog.showAndWait();
    }
}
