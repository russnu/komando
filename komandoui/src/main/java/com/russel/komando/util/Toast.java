package com.russel.komando.util;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Toast {

    public static void showSuccess(Stage owner, String message) {
        show(owner, message, "#4BB543"); // Green
    }

    public static void showError(Stage owner, String message) {
        show(owner, message, "#FF4C4C"); // Red
    }

    public static void show(Stage owner, String message, String bgColor) {
        Popup popup = new Popup();

        Label label = new Label(message);
        label.setStyle("-fx-background-color: " + bgColor + "; -fx-text-fill: white; -fx-padding: 10 20 10 20; -fx-background-radius: 8;");
        label.setOpacity(0); // start transparent

        StackPane pane = new StackPane(label);
        pane.setPadding(new Insets(10));
        pane.setStyle("-fx-background-color: transparent;");
        pane.setAlignment(Pos.CENTER);

        popup.getContent().add(pane);
        popup.setAutoFix(true);
        popup.setAutoHide(true);
        popup.setHideOnEscape(true);

        // Position center-bottom of the window
        popup.show(owner);
        double x = owner.getX() + (owner.getWidth() / 2) - (label.getWidth() / 2);
        double y = owner.getY();
        popup.setX(x);
        popup.setY(y);

        animateToast(popup, label);
    }

    private static void animateToast(Popup popup, Label label) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), label);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        PauseTransition pause = new PauseTransition(Duration.seconds(2));

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), label);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> popup.hide());

        fadeIn.setOnFinished(e -> pause.play());
        pause.setOnFinished(e -> fadeOut.play());

        fadeIn.play();
    }
}
