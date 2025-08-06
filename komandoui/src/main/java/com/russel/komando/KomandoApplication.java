    package com.russel.komando;

    import javafx.animation.PauseTransition;
    import javafx.application.Application;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.text.Font;
    import javafx.stage.Stage;
    import javafx.stage.StageStyle;
    import javafx.util.Duration;

    import java.io.IOException;

    public class KomandoApplication extends Application {
        @Override
        public void start(Stage primaryStage) throws IOException {
            Font.loadFont(getClass().getResourceAsStream("/com/russel/komando/fonts/Poppins-Regular.ttf"), 12);
            Font.loadFont(getClass().getResourceAsStream("/com/russel/komando/fonts/Poppins-Italic.ttf"), 12);
            Font.loadFont(getClass().getResourceAsStream("/com/russel/komando/fonts/Poppins-Bold.ttf"), 12);
            showSplash(primaryStage);
        }

        public static void main(String[] args) {
            launch(args);
        }

        private void showSplash(Stage mainStage) {
            try {
                FXMLLoader splashLoader = new FXMLLoader(getClass().getResource("/com/russel/komando/fxml/Splash.fxml"));
                Parent splashRoot = splashLoader.load();

                Stage splashStage = new Stage();
                splashStage.setResizable(false);

                Scene splashScene = new Scene(splashRoot, 600, 400);
                splashScene.getStylesheets().add(getClass().getResource("css/layout.css").toExternalForm());

                splashStage.setScene(splashScene);
                splashStage.setTitle("Loading...");
                splashStage.initStyle(StageStyle.UNDECORATED); // Optional: no title bar
                splashStage.show();

                // Simulate loading (or perform real tasks in a Task)
                PauseTransition pause = new PauseTransition(Duration.seconds(2.5));
                pause.setOnFinished(event -> {
                    splashStage.close();
                    launchMainStage(mainStage);
                });
                pause.play();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void launchMainStage(Stage stage) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/russel/komando/fxml/Layout.fxml"));
                Parent root = loader.load();
                root.setStyle("-fx-font-family: 'Poppins';");

                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("css/layout.css").toExternalForm());
                stage.setWidth(1200);
                stage.setHeight(800);
                stage.setScene(scene);
                stage.setTitle("Komando");
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
