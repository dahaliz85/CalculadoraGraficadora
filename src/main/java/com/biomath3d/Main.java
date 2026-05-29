package com.biomath3d;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Apunta correctamente a la carpeta ui exclusiva de resources
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/biomath3d/ui/view/MainView.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("BioMath 3D - Calculadora Gráfica");
        primaryStage.setScene(scene);

        primaryStage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        // Carga el icono desde la ubicación exclusiva de resources
        try {
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/biomath3d/ui/img/logo.png")));
        } catch (Exception e) {
            System.out.println("Nota: Icono no encontrado.");
        }

        primaryStage.initStyle(javafx.stage.StageStyle.UNDECORATED);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}