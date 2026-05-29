package com.biomath3d;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cargar estructura FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/biomath3d/ui/MainView.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("BioMath 3D - Calculadora Gráfica");
        primaryStage.setScene(scene);

        // Icono de la ventana
        try {
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/biomath3d/ui/icon.png")));
        } catch (Exception e) {
            System.out.println("Nota: Icono no encontrado.");
        }

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}